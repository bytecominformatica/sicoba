package br.com.clairtonluz.sicoba.service.financeiro.gerencianet.carnet;

import br.com.clairtonluz.sicoba.exception.BadRequestException;
import br.com.clairtonluz.sicoba.model.entity.comercial.Contrato;
import br.com.clairtonluz.sicoba.model.entity.financeiro.gerencianet.carnet.Carnet;
import br.com.clairtonluz.sicoba.model.entity.financeiro.gerencianet.carnet.StatusCarnet;
import br.com.clairtonluz.sicoba.model.entity.financeiro.gerencianet.charge.Charge;
import br.com.clairtonluz.sicoba.model.entity.financeiro.gerencianet.charge.StatusCharge;
import br.com.clairtonluz.sicoba.repository.comercial.ClienteRepository;
import br.com.clairtonluz.sicoba.repository.comercial.ContratoRepository;
import br.com.clairtonluz.sicoba.repository.financeiro.gerencianet.CarnetRepository;
import br.com.clairtonluz.sicoba.repository.financeiro.gerencianet.ChargeRepository;
import br.com.clairtonluz.sicoba.service.financeiro.gerencianet.charge.ChargeService;
import br.com.clairtonluz.sicoba.util.DateUtil;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by clairton on 09/11/16.
 */
@Service
public class CarnetService {

    @Autowired
    private CarnetGNService carnetGNService;
    @Autowired
    private CarnetRepository carnetRepository;
    @Autowired
    private ChargeRepository chargeRepository;
    @Autowired
    private ContratoRepository contratoRepository;
    @Autowired
    private ClienteRepository clienteRepository;

    @Transactional
    public Carnet createCarnet(Carnet carnet) {
        JSONObject resultJson = carnetGNService.createCarnet(carnet);

        if (resultJson.getInt("code") == HttpStatus.OK.value()) {
            JSONObject carnetJson = resultJson.getJSONObject("data");
            JSONArray chargesJson = carnetJson.getJSONArray("charges");

            carnet.setCarnetId(carnetJson.getInt("carnet_id"));
            carnet.setLink(carnetJson.getString("link"));
            carnet.setCover(carnetJson.getString("cover"));
            carnet.setStatus(StatusCarnet.valueOf(carnetJson.getString("status").toUpperCase()));

            carnet = carnetRepository.save(carnet);

            List<Charge> charges = new ArrayList<>();
            for (int i = 0; i < chargesJson.length(); i++) {
                JSONObject it = chargesJson.getJSONObject(i);
                Charge charge = new Charge();
                charge.setCarnet(carnet);
                charge.setChargeId(it.getInt("charge_id"));
                charge.setExpireAt(DateUtil.parseDateISO(it.getString("expire_at")));
                charge.setValue(it.getDouble("value") / 100);
                charge.setBarcode(it.getString("barcode"));
                charge.setUrl(it.getString("url"));
                charge.setStatus(StatusCharge.valueOf(it.getString("status").toUpperCase()));
                charge.setParcel(it.getInt("parcel"));
                charge.setDiscount(carnet.getDiscountSplit());
                charge.setDescription(carnet.getDescription());
                charge.setCliente(carnet.getCliente());

                charges.add(charge);
            }

            Iterable<Charge> chargesSaved = chargeRepository.save(charges);
            charges.clear();
            chargesSaved.forEach(charges::add);

            return carnet;
        } else {
            throw new BadRequestException(resultJson.toString());
        }
    }

    public Charge updateParcelExpireAt(Integer carnetId, Integer parcel, Charge charge) {
        Date expireAt = charge.getExpireAt();
        charge = chargeRepository.findOptionalByCarnet_idAndParcel(carnetId, parcel);
        if (ChargeService.isExpireAtValid(charge, expireAt)) {
            charge.setExpireAt(expireAt);
            if (carnetGNService.updateParcelExpireAt(charge)) {
                charge = chargeRepository.save(charge);
            }
        }
        return charge;
    }

    @Transactional
    public void cancelCarnet(Carnet carnet) {
        if (carnetGNService.cancelCarnet(carnet)) {
            carnet.setStatus(StatusCarnet.CANCELED);
            carnetRepository.save(carnet);
            List<Charge> charges = chargeRepository.findByCarnet_idOrderByExpireAtDesc(carnet.getId());
            charges.forEach((it) -> {
                if (it.isCancelable()) {
                    it.setStatus(StatusCharge.CANCELED);
                }
            });
            chargeRepository.save(charges);
        }
    }

    public void cancelParcel(Charge charge) {
        if (carnetGNService.cancelParcel(charge)) {
            charge.setStatus(StatusCharge.CANCELED);
            chargeRepository.save(charge);
        }
    }

    public boolean updateCarnetMetadata(Carnet carnet) {
        return carnetGNService.updateCarnetMetadata(carnet);
    }

    /**
     * esse metodo é executado assincronamente porque pode demorar muito tempo dependendo da quantidade de carnês
     * retorna false caso algum dos carnês não conseguir executar a operação de atualização
     *
     * @return
     */
    @Async
    public void updateCarnetMetadataAll() {
        Iterable<Carnet> carnets = carnetRepository.findAll();
        for (Carnet c : carnets) {
            carnetGNService.updateCarnetMetadata(c);
        }
    }

    public boolean resendCarnet(Carnet carnet) {
        return carnetGNService.resendCarnet(carnet);
    }

    public boolean resendParcel(Charge charge) {
        return carnetGNService.resendParcel(charge);
    }

    public Carnet findById(Integer id) {
        return carnetRepository.findOne(id);
    }

    public List<Carnet> findByCliente(Integer clienteId) {
        return carnetRepository.findByCliente_id(clienteId);
    }

    public Carnet createModelo(Integer clienteId) {
        Carnet carnet = new Carnet();
        Contrato contrato = contratoRepository.findOptionalByCliente_id(clienteId);
        if (contrato != null) {
            carnet.setCliente(contrato.getCliente());
            carnet.setFirstPay(ChargeService.getNextExpireAt(contrato));
            Double value = contrato.getPlano().getValor();
            if (contrato.getEquipamentoWifi() != null) {
                value += 5;
                carnet.setDiscount(5d);
            }
            carnet.setValue(value);
            carnet.setDescription(String.format("Internet Banda Larga %s", contrato.getPlano().getNome()));
        } else {
            carnet.setCliente(clienteRepository.findOne(clienteId));
            carnet.setFirstPay(new Date());
        }

        carnet.setMessage(String.format("Olá, %s! \nObrigado por escolher a Bytecom Informática.", carnet.getCliente().getNome()));
        carnet.setStatus(StatusCarnet.ACTIVE);
        carnet.setRepeats(12);
        carnet.setSplitItems(false);

        return carnet;
    }
}
