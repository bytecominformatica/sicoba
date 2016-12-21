package br.com.clairtonluz.sicoba.service.financeiro.gerencianet.charge;

import br.com.clairtonluz.sicoba.exception.BadRequestException;
import br.com.clairtonluz.sicoba.exception.ConflitException;
import br.com.clairtonluz.sicoba.model.entity.comercial.Contrato;
import br.com.clairtonluz.sicoba.model.entity.financeiro.gerencianet.charge.Charge;
import br.com.clairtonluz.sicoba.model.entity.financeiro.gerencianet.charge.PaymentType;
import br.com.clairtonluz.sicoba.model.entity.financeiro.gerencianet.charge.StatusCharge;
import br.com.clairtonluz.sicoba.repository.financeiro.gerencianet.ChargeRepository;
import br.com.clairtonluz.sicoba.service.comercial.ContratoService;
import br.com.clairtonluz.sicoba.service.financeiro.gerencianet.GNService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by clairton on 09/11/16.
 */
@Service
public class ChargeService {

    @Autowired
    private ChargeRepository chargeRepository;
    @Autowired
    private ContratoService contratoService;
    @Autowired
    private ChargeGNService chargeGNService;

    public Charge createChargeBillet(Charge charge) {
        charge = createCharge(charge);
        charge = setPaymentToBankingBillet(charge);
        return charge;
    }

    public Charge createChargeLinkDePagamento(Charge charge) {
        charge = createCharge(charge);
        charge = gerarLinkDePagamento(charge);
        return charge;
    }

    @Transactional
    public Charge createCharge(Charge charge) {
        Contrato contrato = contratoService.buscarPorCliente(charge.getCliente().getId());
        JSONObject createChargeResult = chargeGNService.createCharge(contrato, charge);

        if (createChargeResult.getInt("code") == HttpStatus.OK.value()) {
            JSONObject data = createChargeResult.getJSONObject("data");

            charge.setChargeId(data.getInt("charge_id"));
            charge.setStatus(StatusCharge.valueOf(data.getString("status").toUpperCase()));

            charge = save(charge);

        } else {
            throw new BadRequestException(createChargeResult.toString());
        }
        return charge;
    }

    @Transactional
    public Charge setPaymentToBankingBillet(Charge charge) {
        JSONObject response = chargeGNService.setPaymentToBankingBilletGN(charge);
        if (!GNService.isOk(response))
            throw new ConflitException("Ocorreu um erro ao tentar definir a forma de pagamento para boleto");

        JSONObject data = response.getJSONObject("data");

        charge.setBarcode(data.getString("barcode"));
        charge.setUrl(data.getString("link"));
        charge.setStatus(StatusCharge.valueOf(data.getString("status").toUpperCase()));
        charge.setPayment(PaymentType.valueOf(data.getString("payment").toUpperCase()));

        return chargeRepository.save(charge);
    }

    /**
     * para gerar um link de pagamento a cobrança deve está com status NEW
     *
     * @param charge
     * @return
     */
    @Transactional
    public Charge gerarLinkDePagamento(Charge charge) {
        JSONObject response = chargeGNService.linkCharge(charge);
        if (GNService.isOk(response)) {
            JSONObject data = response.getJSONObject("data");
            charge.setStatus(StatusCharge.valueOf(data.getString("status").toUpperCase()));
            charge.setPaymentUrl(data.getString("payment_url"));
            charge.setPayment(PaymentType.valueOf(data.getString("payment_method").toUpperCase()));
            charge = save(charge);
        }
        return charge;
    }

    @Transactional
    public Charge cancelCharge(Charge charge) {
        JSONObject response = chargeGNService.cancelCharge(charge);
        if (GNService.isOk(response)) {
            charge.setStatus(StatusCharge.CANCELED);
            charge = save(charge);
        }
        return charge;
    }

    @Transactional
    public Charge updateBilletExpireAt(Charge charge) {
        if (chargeGNService.updateBilletExpireAt(charge)) {
            charge = save(charge);
        }
        return charge;
    }

    @Transactional
    public boolean updateChargeMetadata(Charge charge) {
        return chargeGNService.updateChargeMetadata(charge);
    }

    @Transactional
    public void resendBillet(Charge charge) {
        chargeGNService.resendBillet(charge);
    }

    @Transactional
    public Charge save(Charge charge) {
        return chargeRepository.save(charge);
    }

    public Charge findById(Integer id) {
        return chargeRepository.findOne(id);
    }

    public List<Charge> findByCliente(Integer clienteId) {
        return chargeRepository.findByCliente_id(clienteId);
    }

    public Charge findByCarnetAndParcel(Integer carnetId, Integer parcel) {
        return chargeRepository.findOptionalByCarnet_idAndParcel(carnetId, parcel);
    }
}
