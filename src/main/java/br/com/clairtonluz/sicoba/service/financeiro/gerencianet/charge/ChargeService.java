package br.com.clairtonluz.sicoba.service.financeiro.gerencianet.charge;

import br.com.clairtonluz.sicoba.exception.BadRequestException;
import br.com.clairtonluz.sicoba.exception.ConflitException;
import br.com.clairtonluz.sicoba.model.entity.comercial.Cliente;
import br.com.clairtonluz.sicoba.model.entity.comercial.Contrato;
import br.com.clairtonluz.sicoba.model.entity.comercial.StatusCliente;
import br.com.clairtonluz.sicoba.model.entity.financeiro.gerencianet.charge.Charge;
import br.com.clairtonluz.sicoba.model.entity.financeiro.gerencianet.charge.ChargeVO;
import br.com.clairtonluz.sicoba.model.entity.financeiro.gerencianet.charge.PaymentType;
import br.com.clairtonluz.sicoba.model.entity.financeiro.gerencianet.charge.StatusCharge;
import br.com.clairtonluz.sicoba.repository.comercial.ClienteRepository;
import br.com.clairtonluz.sicoba.repository.comercial.ContratoRepository;
import br.com.clairtonluz.sicoba.repository.financeiro.gerencianet.ChargeRepository;
import br.com.clairtonluz.sicoba.service.financeiro.gerencianet.GNService;
import br.com.clairtonluz.sicoba.service.financeiro.gerencianet.carnet.CarnetGNService;
import br.com.clairtonluz.sicoba.service.notification.EmailService;
import br.com.clairtonluz.sicoba.util.StringUtil;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by clairton on 09/11/16.
 */
@Service
public class ChargeService {
    private final ChargeRepository chargeRepository;
    private final ContratoRepository contratoRepository;
    private final ClienteRepository clienteRepository;
    private final ChargeGNService chargeGNService;
    private final CarnetGNService carnetGNService;
    private final EmailService emailService;

    @Autowired
    public ChargeService(ChargeRepository chargeRepository, ContratoRepository contratoRepository, ClienteRepository clienteRepository, ChargeGNService chargeGNService, CarnetGNService carnetGNService, EmailService emailService) {
        this.chargeRepository = chargeRepository;
        this.contratoRepository = contratoRepository;
        this.clienteRepository = clienteRepository;
        this.chargeGNService = chargeGNService;
        this.carnetGNService = carnetGNService;
        this.emailService = emailService;
    }

    public static boolean isExpireAtValid(Charge charge, LocalDate expireAt) {
        if (!charge.getExpireAt().isBefore(expireAt)) {
            throw new ConflitException("A nova data de vencimento deve ser maior do que a anterior");
        }

        if (expireAt.isBefore(LocalDate.now())) {
            throw new ConflitException("O vencimento deve ser maior ou igual a data atual");
        }

        if (!StatusCharge.WAITING.equals(charge.getStatus()) && !StatusCharge.UNPAID.equals(charge.getStatus())) {
            throw new ConflitException("Apenas transações com status [waiting] ou [unpaid] podem ser atualizadas");
        }
        return true;
    }

    public static LocalDate getNextExpireAt(Contrato contrato) {
        return LocalDate.now().plusMonths(1).withDayOfMonth(contrato.getVencimento());
    }

    @Transactional
    public Charge createCharge(Charge charge) {
        JSONObject createChargeResult = chargeGNService.createCharge(charge);

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

        charge = chargeRepository.save(charge);
        return chargeRepository.getOne(charge.getId());
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
        return chargeRepository.findOptionalById(charge.getId());
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

        charge = cancelCharge(chargeAtual);

        emailService.sendToAdmin(subject, content);
        return charge;
    }

    @Transactional
    public Charge cancelCharge(Charge charge) {
        boolean canceled = Objects.isNull(charge.getCarnet()) ? chargeGNService.cancelCharge(charge) : carnetGNService.cancelParcel(charge);
        if (canceled) {
            charge.setStatus(StatusCharge.CANCELED);
            charge = save(charge);
        }
        return chargeRepository.findOptionalById(charge.getId());
    }

    @Transactional
    public Charge updateBilletExpireAt(Charge charge) {
        LocalDate expireAt = charge.getExpireAt();
        charge = findById(charge.getId());
        if (isExpireAtValid(charge, expireAt)) {
            charge.setExpireAt(expireAt);
            if (chargeGNService.updateBilletExpireAt(charge)) {
                charge = save(charge);
            }
        }
        return chargeRepository.findOptionalById(charge.getId());
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
        return chargeRepository.findOptionalById(chargeRepository.save(charge).getId());
    }

    public Charge findById(Integer id) {
        return chargeRepository.findOptionalById(id);
    }

    public <T> List<T> findByCliente(Integer clienteId) {
        return chargeRepository.findByCliente_idOrderByExpireAtDesc(clienteId);
    }

    public <T> List<T> findCurrentByClient(Integer clienteId) {
        LocalDate begin = LocalDate.now().minusMonths(2);
        LocalDate finish = LocalDate.now().plusMonths(2);
        return chargeRepository.findCurrentByClientAndDate(clienteId, begin, finish);
    }

    public <T> List<T> findByCarnet(Integer carnetId) {
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
            charge.setValue(value);
            charge.setDescription(String.format("Internet Banda Larga %s", contrato.getPlano().getNome()));
        } else {
            charge.setCliente(clienteRepository.getOne(clienteId));
            charge.setExpireAt(LocalDate.now());
        }

        charge.setMessage(String.format("Olá, %s! \nObrigado por escolher a Bytecom Informática.", charge.getCliente().getNome()));
        charge.setStatus(StatusCharge.NEW);

        return charge;
    }

    public <T> List<T> overdue(LocalDate dateReference) {
        return chargeRepository.findByExpireAtLessThanAndStatusNotInAndCliente_statusNotOrderByExpireAt(dateReference,
                Arrays.asList(StatusCharge.PAID, StatusCharge.CANCELED), StatusCliente.CANCELADO);
    }

    public List<ChargeVO> findByPaymentDateAndStatusAndGerencianetAccount(LocalDate start, LocalDate end, StatusCharge status, Integer gerencianetAccountId) {
        List<Charge> chargeList;
        if (status == null && gerencianetAccountId == null) {
            chargeList = chargeRepository.findByPaidAtBetween(start.atStartOfDay(), end.atTime(23, 59, 59));
            chargeList = removeCanceled(chargeList);
        } else if (status == null) {
            chargeList = chargeRepository.findByPaidAtBetweenAndGerencianetAccount_id(start.atStartOfDay(), end.atTime(23, 59, 59), gerencianetAccountId);
            chargeList = removeCanceled(chargeList);
        } else if (gerencianetAccountId == null) {
            chargeList = chargeRepository.findByPaidAtBetweenAndStatus(start.atStartOfDay(), end.atTime(23, 59, 59), status);
        } else {
            chargeList = chargeRepository.findByPaidAtBetweenAndStatusAndGerencianetAccount_id(start.atStartOfDay(), end.atTime(23, 59, 59), status, gerencianetAccountId);
        }

        return chargeList.stream().map(ChargeVO::new).collect(Collectors.toList());
    }

    private List<Charge> removeCanceled(List<Charge> chargeList) {
        return chargeList.stream()
                .filter(charge -> (charge.getManualPayment() != null && charge.getManualPayment())  || !StatusCharge.CANCELED.equals(charge.getStatus()))
                .collect(Collectors.toList());
    }

    public List<ChargeVO> findByExpirationDateAndStatusAndGerencianetAccount(LocalDate start, LocalDate end, StatusCharge status, Integer gerencianetAccountId) {
        List<Charge> chargeList;
        if (status == null && gerencianetAccountId == null) {
            chargeList = chargeRepository.findByExpireAtBetween(start, end);
            chargeList = removeCanceled(chargeList);
        } else if (status == null) {
            chargeList = chargeRepository.findByExpireAtBetweenAndGerencianetAccount_id(start, end, gerencianetAccountId);
            chargeList = removeCanceled(chargeList);
        } else if (gerencianetAccountId == null) {
            chargeList = chargeRepository.findByExpireAtBetweenAndStatus(start, end, status);
        } else {
            chargeList = chargeRepository.findByExpireAtBetweenAndStatusAndGerencianetAccount_id(start, end, status, gerencianetAccountId);
        }
        return chargeList.stream().map(ChargeVO::new).collect(Collectors.toList());
    }

    public <T> List<T> buscarNaoVencidosPorCliente(Cliente cliente) {
        return chargeRepository.findByClienteAndStatusAndExpireAtGreaterThan(cliente, StatusCharge.WAITING, LocalDate.now());
    }

    public void cancelarCobrancasNaoVencidas(Cliente cliente) {
        List<Charge> chargesNaoVencidos = buscarNaoVencidosPorCliente(cliente);
        chargesNaoVencidos.forEach(this::cancelCharge);
    }
}
