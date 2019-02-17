package br.com.clairtonluz.sicoba.service.financeiro.gerencianet.charge;

import br.com.clairtonluz.sicoba.exception.ConflitException;
import br.com.clairtonluz.sicoba.model.entity.financeiro.gerencianet.GerencianetAccount;
import br.com.clairtonluz.sicoba.model.entity.financeiro.gerencianet.charge.Charge;
import br.com.clairtonluz.sicoba.service.financeiro.gerencianet.GNService;
import br.com.clairtonluz.sicoba.util.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by clairton on 09/11/16.
 */
@Service
class ChargeGNService {

    private static final String CREATE_CHARGE = "createCharge";
    private static final String DETAIL_CHARGE = "detailCharge";
    private static final String UPDATE_CHARGE_METADATA = "updateChargeMetadata";
    private static final String UPDATE_BILLET = "updateBillet";
    private static final String PAY_CHARGE = "payCharge";
    private static final String CANCEL_CHARGE = "cancelCharge";
    private static final String RESEND_BILLET = "resendBillet";
    private static final String LINK_CHARGE = "linkCharge";
    private final GNService gnService;

    @Autowired
    ChargeGNService(GNService gnService) {
        this.gnService = gnService;
    }

    /**
     * Cria um cobrança mas não define a forma de pagamento ainda.
     *
     * @param charge
     * @return
     */
    JSONObject createCharge(Charge charge) {
        GerencianetAccount account = charge.getGerencianetAccount();
        JSONArray items = new JSONArray().put(GNService.createItem(charge.getDescription(), charge.getValue()));

        JSONObject body = new JSONObject();
        body.put("items", items);
        body.put("metadata", GNService.createMetadata(gnService.createNotificationUrl(account), charge.getId()));

        return gnService.call(account, CREATE_CHARGE, body);
    }

    /**
     * Define a forma de pagamento como boleto. Será definido no atributo charge.url o link para o boleto, assim o cliente
     * poderá acessar esse link para pagar ou imprimir o boleto.
     *
     * @param charge
     * @return
     */
    JSONObject setPaymentToBankingBilletGN(Charge charge) {
        GerencianetAccount account = charge.getGerencianetAccount();

        Map<String, String> params = new HashMap<>();
        params.put("id", charge.getChargeId().toString());

        JSONObject customer = GNService.createConsumer(charge.getCliente(), gnService.isNotifyClient(account));
        JSONObject configurations = GNService.createConfigurations(account);
        JSONObject discount = GNService.createDiscount(charge.getDiscount());

        JSONObject bankingBillet = new JSONObject();
        bankingBillet.put("expire_at", charge.getExpireAt()).toString();
        bankingBillet.put("customer", customer);
        bankingBillet.put("configurations", configurations);
        if (discount != null) {
            bankingBillet.put("discount", discount);
        }

        JSONObject payment = new JSONObject();
        payment.put("banking_billet", bankingBillet);

        JSONObject body = new JSONObject();
        body.put("payment", payment);

        return gnService.call(account, PAY_CHARGE, params, body);
    }


    /**
     * Criar uma link de uma página de pagamento para que o cliente possa pagar com boleto ou cartão de crédito
     * através desse link de pagamento
     *
     * @param charge
     * @return
     */
    JSONObject linkCharge(Charge charge) {
        Map<String, String> params = new HashMap<>();
        params.put("id", charge.getChargeId().toString());

        JSONObject body = new JSONObject();
        if (charge.getDiscount() != null && charge.getDiscount() > 0) {
            body.put("billet_discount", charge.getDiscount() * 100);
        }

        body.put("message", charge.getMessage());

        body.put("expire_at", charge.getExpireAt()).toString();
        body.put("request_delivery_address", false);
        body.put("payment_method", "all");

        return gnService.call(charge.getGerencianetAccount(), LINK_CHARGE, params, body);
    }

    /**
     * Cancela uma cobrança
     *
     * @param charge
     * @return
     */
    boolean cancelCharge(Charge charge) {
        Map<String, String> params = new HashMap<>();
        params.put("id", charge.getChargeId().toString());

        JSONObject call = gnService.call(charge.getGerencianetAccount(), CANCEL_CHARGE, params);
        return GNService.isOk(call);
    }

    /**
     * atualiza a data do vencimento de uma cobrança
     *
     * @param charge
     * @return
     */
    boolean updateBilletExpireAt(Charge charge) {
        Map<String, String> params = new HashMap<>();
        params.put("id", charge.getChargeId().toString());

        JSONObject body = new JSONObject();
        body.put("expire_at", charge.getExpireAt()).toString();

        return GNService.isOk(gnService.call(charge.getGerencianetAccount(), UPDATE_BILLET, params, body));
    }

    /**
     * Atualiza a url de notificação e o customId da cobrança
     *
     * @param charge
     * @return
     */
    boolean updateChargeMetadata(Charge charge) {
        Map<String, String> params = new HashMap<>();
        params.put("id", charge.getChargeId().toString());

        GerencianetAccount account = charge.getGerencianetAccount();
        JSONObject body = GNService.createMetadata(gnService.createNotificationUrl(account), charge.getId());

        return GNService.isOk(gnService.call(account, UPDATE_CHARGE_METADATA, params, body));
    }

    /**
     * retorna informações da cobrança
     *
     * @param charge
     * @return
     */
    JSONObject detailCharge(Charge charge) {
        Map<String, String> params = new HashMap<>();
        params.put("id", charge.getChargeId().toString());
        return gnService.call(charge.getGerencianetAccount(), DETAIL_CHARGE, params);
    }

    /**
     * reenvia a cobrança para o email do cliente
     *
     * @param charge
     * @return
     */
    boolean resendBillet(Charge charge) {
        if (StringUtils.isBlank(charge.getCliente().getEmail())) {
            throw new ConflitException("Cliente não possui email");
        }

        Map<String, String> params = new HashMap<>();
        params.put("id", charge.getChargeId().toString());

        JSONObject body = new JSONObject();
        body.put("email", charge.getCliente().getEmail());

        return GNService.isOk(gnService.call(charge.getGerencianetAccount(), RESEND_BILLET, params, body));
    }
}
