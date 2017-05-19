package br.com.clairtonluz.sicoba.service.financeiro.gerencianet.charge;

import br.com.clairtonluz.sicoba.exception.ConflitException;
import br.com.clairtonluz.sicoba.model.entity.comercial.Contrato;
import br.com.clairtonluz.sicoba.model.entity.financeiro.gerencianet.Credentials;
import br.com.clairtonluz.sicoba.model.entity.financeiro.gerencianet.charge.Charge;
import br.com.clairtonluz.sicoba.service.financeiro.gerencianet.GNService;
import br.com.clairtonluz.sicoba.util.DateUtil;
import br.com.clairtonluz.sicoba.util.StringUtil;
import org.json.JSONArray;
import org.json.JSONObject;
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
    public static final String LINK_CHARGE = "linkCharge";


    /**
     * Cria um cobrança mas não define a forma de pagamento ainda.
     *
     * @param contrato
     * @param charge
     * @return
     */
    JSONObject createCharge(Contrato contrato, Charge charge) {
        JSONArray items = new JSONArray().put(GNService.createItem(charge.getDescription(), charge.getValue()));

        JSONObject body = new JSONObject();
        body.put("items", items);
        body.put("metadata", GNService.createMetadata(Credentials.getInstance().getNotificationUrl(), charge.getId()));

        return GNService.call(CREATE_CHARGE, body);
    }

    /**
     * Define a forma de pagamento como boleto. Será definido no atributo charge.url o link para o boleto, assim o cliente
     * poderá acessar esse link para pagar ou imprimir o boleto.
     *
     * @param charge
     * @return
     */
    JSONObject setPaymentToBankingBilletGN(Charge charge) {

        Map<String, String> params = new HashMap<>();
        params.put("id", charge.getChargeId().toString());

        JSONObject customer = GNService.createConsumer(charge.getCliente(), false);
//        JSONArray instructions = GNService.createInstructions(charge);
        JSONObject configurations = GNService.createConfigurations();
        System.out.println(charge.getDiscount());
        JSONObject discount = GNService.createDiscount(charge.getDiscount());

        JSONObject bankingBillet = new JSONObject();
        bankingBillet.put("expire_at", DateUtil.formatISO(charge.getExpireAt()));
        bankingBillet.put("customer", customer);
//        bankingBillet.put("instructions", instructions);
        bankingBillet.put("configurations", configurations);
        System.out.println("teste2");
        System.out.println(discount);
        if (discount != null) {
            bankingBillet.put("discount", discount);
        }

        JSONObject payment = new JSONObject();
        payment.put("banking_billet", bankingBillet);

        JSONObject body = new JSONObject();
        body.put("payment", payment);

        return GNService.call(PAY_CHARGE, params, body);
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

        body.put("expire_at", DateUtil.formatISO(charge.getExpireAt()));
        body.put("request_delivery_address", false);
        body.put("payment_method", "all");

        return GNService.call(LINK_CHARGE, params, body);
    }

    /**
     * Cancela uma cobrança
     *
     * @param charge
     * @return
     */
    JSONObject cancelCharge(Charge charge) {
        Map<String, String> params = new HashMap<>();
        params.put("id", charge.getChargeId().toString());

        return GNService.call(CANCEL_CHARGE, params);
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
        body.put("expire_at", DateUtil.formatISO(charge.getExpireAt()));

        return GNService.isOk(GNService.call(UPDATE_BILLET, params, body));
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

        JSONObject body = GNService.createMetadata(Credentials.getInstance().getNotificationUrl(), charge.getId());

        return GNService.isOk(GNService.call(UPDATE_CHARGE_METADATA, params, body));
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
        return GNService.call(DETAIL_CHARGE, params);
    }

    /**
     * reenvia a cobrança para o email do cliente
     *
     * @param charge
     * @return
     */
    boolean resendBillet(Charge charge) {
        if (StringUtil.isEmpty(charge.getCliente().getEmail())) {
            throw new ConflitException("Cliente não possui email");
        }

        Map<String, String> params = new HashMap<>();
        params.put("id", charge.getChargeId().toString());

        JSONObject body = new JSONObject();
        body.put("email", charge.getCliente().getEmail());

        return GNService.isOk(GNService.call(RESEND_BILLET, params, body));
    }
}
