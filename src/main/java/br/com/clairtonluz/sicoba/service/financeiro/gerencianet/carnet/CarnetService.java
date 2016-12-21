package br.com.clairtonluz.sicoba.service.financeiro.gerencianet.carnet;

import br.com.clairtonluz.sicoba.exception.BadRequestException;
import br.com.clairtonluz.sicoba.model.entity.comercial.Contrato;
import br.com.clairtonluz.sicoba.model.entity.financeiro.gerencianet.carnet.Carnet;
import br.com.clairtonluz.sicoba.model.entity.financeiro.gerencianet.carnet.StatusCarnet;
import br.com.clairtonluz.sicoba.model.entity.financeiro.gerencianet.charge.Charge;
import br.com.clairtonluz.sicoba.model.entity.financeiro.gerencianet.charge.StatusCharge;
import br.com.clairtonluz.sicoba.model.pojo.financeiro.gerencianet.CarnetPojo;
import br.com.clairtonluz.sicoba.repository.comercial.ContratoRepository;
import br.com.clairtonluz.sicoba.repository.financeiro.gerencianet.CarnetRepository;
import br.com.clairtonluz.sicoba.repository.financeiro.gerencianet.ChargeRepository;
import br.com.clairtonluz.sicoba.util.DateUtil;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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

    @Transactional
    public List<Charge> createCarnet(CarnetPojo carnetPojo) {
        Contrato contrato = contratoRepository.findOptionalByCliente_id(carnetPojo.getClienteId());
        Double desconto = carnetPojo.getDesconto();
        JSONObject resultJson = carnetGNService.createCarnet(contrato, carnetPojo.getDataInicio(), carnetPojo.getValor(), desconto, carnetPojo.getQuantidadeParcela());

        if (resultJson.getInt("code") == HttpStatus.OK.value()) {
            JSONObject carnetJson = resultJson.getJSONObject("data");
            JSONArray chargesJson = carnetJson.getJSONArray("charges");

            Carnet carnet = new Carnet();
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
                charge.setParcel(it.getInt("parcel"));
                charge.setValue(it.getDouble("value") / 100);
                charge.setDiscount(desconto);
                charge.setExpireAt(DateUtil.parseDateISO(it.getString("expire_at")));
                charges.add(charge);
            }

            Iterable<Charge> chargesSaved = chargeRepository.save(charges);
            charges.clear();
            chargesSaved.forEach(charges::add);

            return charges;
        } else {
            throw new BadRequestException(resultJson.toString());
        }
    }

    public void updateParcelExpireAt(Charge charge) {
        JSONObject jsonObject = carnetGNService.updateParcelExpireAt(charge);
    }

    @Transactional
    public void cancelCarnet(Carnet carnet) {
        if (carnetGNService.cancelCarnet(carnet)) {
            carnet.setStatus(StatusCarnet.CANCELED);
            carnetRepository.save(carnet);
            List<Charge> charges = chargeRepository.findByCarnet_id(carnet.getId());
            charges.forEach(it -> it.setStatus(StatusCharge.CANCELED));
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

    public boolean resendCarnet(Carnet carnet) {
        return carnetGNService.resendCarnet(carnet);
    }

    public boolean resendParcel(Charge charge) {
        return carnetGNService.resendParcel(charge);
    }

}
