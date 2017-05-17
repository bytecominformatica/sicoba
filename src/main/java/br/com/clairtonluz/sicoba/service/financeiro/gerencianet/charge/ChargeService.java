package br.com.clairtonluz.sicoba.service.financeiro.gerencianet.charge;

import br.com.clairtonluz.sicoba.exception.BadRequestException;
import br.com.clairtonluz.sicoba.exception.ConflitException;
import br.com.clairtonluz.sicoba.model.entity.comercial.Contrato;
import br.com.clairtonluz.sicoba.model.entity.financeiro.gerencianet.charge.Charge;
import br.com.clairtonluz.sicoba.model.entity.financeiro.gerencianet.charge.PaymentType;
import br.com.clairtonluz.sicoba.model.entity.financeiro.gerencianet.charge.StatusCharge;
import br.com.clairtonluz.sicoba.repository.comercial.ClienteRepository;
import br.com.clairtonluz.sicoba.repository.comercial.ContratoRepository;
import br.com.clairtonluz.sicoba.repository.financeiro.gerencianet.ChargeRepository;
import br.com.clairtonluz.sicoba.service.financeiro.gerencianet.GNService;
import br.com.clairtonluz.sicoba.util.DateUtil;
import br.com.clairtonluz.sicoba.util.SendEmail;
import br.com.clairtonluz.sicoba.util.StringUtil;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

/**
 * Created by clairton on 09/11/16.
 */
@Service
public class ChargeService {
    @Autowired
    private ChargeRepository chargeRepository;
    @Autowired
    private ContratoRepository contratoRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private ChargeGNService chargeGNService;

    public Charge createChargeBillet(Charge charge) {
        charge = createCharge(charge);
        charge = setPaymentToBankingBillet(charge);
        return charge;
    }

    public Charge createChargeLinkDePagamento(Charge charge) {
        charge = createCharge(charge);
        charge = createPaymentLink(charge);
        return charge;
    }

    @Transactional
    public Charge createCharge(Charge charge) {
        Contrato contrato = contratoRepository.findOptionalByCliente_id(charge.getCliente().getId());
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
    public Charge createPaymentLink(Charge charge) {
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

    public Charge manualPayment(Charge charge) {
        Charge chargeAtual = findById(charge.getId());
        chargeAtual.setPaidValue(charge.getPaidValue());
        chargeAtual.setPaidAt(charge.getPaidAt());
        chargeAtual.setManualPayment(charge.getManualPayment());

        String subject = "[CHARGE] Baixa manual da cobrança " + chargeAtual.getId();
        String content = String.format("Cobrança %d do cliente %d - %s no valor de %s foi baixada manualmente com o valor de %s",
                chargeAtual.getId(), chargeAtual.getCliente().getId(), chargeAtual.getCliente().getNome(),
                StringUtil.formatCurrence(chargeAtual.getValue()), StringUtil.formatCurrence(chargeAtual.getPaidValue()));

        chargeAtual = cancelCharge(chargeAtual);

        SendEmail.sendToAdmin(subject, content);
        return chargeAtual; // cancela o boleto no integrador e salva a cobrança com as alterações
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
        Date expireAt = charge.getExpireAt();
        charge = findById(charge.getId());
        if (isExpireAtValid(charge, expireAt)) {
            charge.setExpireAt(expireAt);
            if (chargeGNService.updateBilletExpireAt(charge)) {
                charge = save(charge);
            }
        }
        return charge;
    }

    public static boolean isExpireAtValid(Charge charge, Date expireAt) {
        if (!charge.getExpireAt().before(expireAt)) {
            throw new ConflitException("A nova data de vencimento deve ser maior do que a anterior");
        }

        if (DateUtil.isPast(expireAt)) {
            throw new ConflitException("O vencimento deve ser maior ou igual a data atual");
        }

        if (!StatusCharge.WAITING.equals(charge.getStatus()) && !StatusCharge.UNPAID.equals(charge.getStatus())) {
            throw new ConflitException("Apenas transações com status [waiting] ou [unpaid] podem ser atualizadas");
        }
        return true;
    }

    @Transactional
    public boolean updateChargeMetadata(Charge charge) {
        return chargeGNService.updateChargeMetadata(charge);
    }

    @Async
    public void updateCarnetMetadataAll() {
        List<Charge> charges = chargeRepository.findByCarnetIsNullAndStatusNot(StatusCharge.PAID);
        for (Charge c : charges) {
            chargeGNService.updateChargeMetadata(c);
        }
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
        return chargeRepository.findByCliente_idOrderByExpireAtDesc(clienteId);
    }

    public List<Charge> findByCarnet(Integer carnetId) {
        return chargeRepository.findByCarnet_id(carnetId);
    }

    public Charge findByCarnetAndParcel(Integer carnetId, Integer parcel) {
        return chargeRepository.findOptionalByCarnet_idAndParcel(carnetId, parcel);
    }

    public Charge createModelo(Integer clienteId) {
        Charge charge = new Charge();
        Contrato contrato = contratoRepository.findOptionalByCliente_id(clienteId);
        if (contrato != null) {
            charge.setCliente(contrato.getCliente());
            charge.setExpireAt(getNextExpireAt(contrato));
            Double value = contrato.getPlano().getValor();
            if (contrato.getEquipamentoWifi() != null) {
                value += 5;
                charge.setDiscount(5d);
            }
            charge.setValue(value);
            charge.setDescription(String.format("Internet Banda Larga %s", contrato.getPlano().getNome()));
        } else {
            charge.setCliente(clienteRepository.findOne(clienteId));
            charge.setExpireAt(new Date());
        }

        charge.setMessage(String.format("Olá, %s! \nObrigado por escolher a Bytecom Informática.", charge.getCliente().getNome()));
        charge.setStatus(StatusCharge.NEW);

        return charge;
    }

    public static Date getNextExpireAt(Contrato contrato) {
        return DateUtil.toDate(LocalDate.now().plusMonths(1).withDayOfMonth(contrato.getVencimento()));
    }

    public List<Charge> overdue() {
        return chargeRepository.overdue(new Date());
    }

}
