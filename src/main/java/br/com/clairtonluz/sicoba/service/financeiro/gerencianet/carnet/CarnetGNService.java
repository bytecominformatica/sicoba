package br.com.clairtonluz.sicoba.service.financeiro.gerencianet.carnet;

import br.com.clairtonluz.sicoba.exception.ConflitException;
import br.com.clairtonluz.sicoba.model.entity.comercial.Contrato;
import br.com.clairtonluz.sicoba.model.entity.financeiro.gerencianet.Credentials;
import br.com.clairtonluz.sicoba.model.entity.financeiro.gerencianet.carnet.Carnet;
import br.com.clairtonluz.sicoba.model.entity.financeiro.gerencianet.charge.Charge;
import br.com.clairtonluz.sicoba.service.financeiro.gerencianet.GNService;
import br.com.clairtonluz.sicoba.util.DateUtil;
import br.com.clairtonluz.sicoba.util.StringUtil;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by clairton on 09/11/16.
 */
@Service
class CarnetGNService {

    private static final String CREATE_CARNET = "createCarnet";
    private static final String DETAIL_CARNET = "detailCarnet";
    private static final String UPDATE_PARCEL = "updateParcel";
    private static final String UPDATE_CARNET_METADATA = "updateCarnetMetadata";
    private static final String CANCEL_CARNET = "cancelCarnet";
    private static final String CANCEL_PARCEL = "cancelParcel";
    private static final String RESEND_CARNET = "resendCarnet";
    private static final String RESEND_PARCEL = "resendParcel";

    JSONObject createCarnet(Contrato contrato, Date vencimento, Double valor, Double desconto, Integer quantidadeParcela) {
         /* *********  Set credentials parameters ******** */
        String item = String.format("Internet Banda Larga %s", contrato.getPlano().getNome());
        JSONArray items = new JSONArray().put(GNService.createItem(item, valor));
        JSONObject customer = GNService.createConsumer(contrato.getCliente(), false);
        JSONArray instructions = GNService.createInstructions(desconto);

        JSONObject body = new JSONObject();
        body.put("items", items);
        body.put("customer", customer);
        body.put("expire_at", DateUtil.formatISO(vencimento));
        body.put("repeats", quantidadeParcela);
        body.put("split_items", false);
        body.put("instructions", instructions);
        body.put("metadata", GNService.createMetadata(Credentials.getInstance().getNotificationUrl()));

        return GNService.call(CREATE_CARNET, body);
    }

    boolean updateParcelExpireAt(Charge charge) {
        if (charge.getCarnet() == null || charge.getCarnet().getCarnetId() == null) {
            throw new ConflitException("Está cobrança não fas parte de um carnê");
        }
        Map<String, String> params = new HashMap<>();
        params.put("id", charge.getCarnet().getCarnetId().toString());
        params.put("parcel", charge.getParcel().toString());

        JSONObject body = new JSONObject();
        body.put("expire_at", DateUtil.formatISO(charge.getExpireAt()));

        return GNService.isOk(GNService.call(UPDATE_PARCEL, params, body));
    }

    boolean cancelCarnet(Carnet carnet) {
        Map<String, String> params = new HashMap<>();
        params.put("id", carnet.getCarnetId().toString());

        JSONObject response = GNService.call(CANCEL_CARNET, params);
        return GNService.isOk(response);
    }

    boolean cancelParcel(Charge charge) {
        if (charge.getCarnet() == null || charge.getCarnet().getCarnetId() == null) {
            throw new ConflitException("Está cobrança não fas parte de um carnê");
        }
        Map<String, String> params = new HashMap<>();
        params.put("id", charge.getCarnet().getCarnetId().toString());
        params.put("parcel", charge.getParcel().toString());

        JSONObject response = GNService.call(CANCEL_PARCEL, params);
        return GNService.isOk(response);
    }

    boolean updateCarnetMetadata(Carnet carnet) {
        Map<String, String> params = new HashMap<>();
        params.put("id", carnet.getCarnetId().toString());
        JSONObject body = GNService.createMetadata(Credentials.getInstance().getNotificationUrl(), carnet.getId());
        JSONObject response = GNService.call(UPDATE_CARNET_METADATA, params, body);
        return GNService.isOk(response);
    }

    JSONObject detailCarnet(Carnet carnet) {
        Map<String, String> params = new HashMap<>();
        params.put("id", carnet.getCarnetId().toString());
        return GNService.call(DETAIL_CARNET, params);
    }

    boolean resendCarnet(Carnet carnet) {
        if (StringUtil.isEmpty(carnet.getCliente().getEmail())) {
            throw new ConflitException("Cliente não possui email");
        }

        Map<String, String> params = new HashMap<>();
        params.put("id", carnet.getCarnetId().toString());

        JSONObject body = new JSONObject();
        body.put("email", carnet.getCliente().getEmail());

        return GNService.isOk(GNService.call(RESEND_CARNET, params, body));
    }

    boolean resendParcel(Charge charge) {
        if (StringUtil.isEmpty(charge.getCliente().getEmail())) {
            throw new ConflitException("Cliente não possui email");
        }

        Map<String, String> params = new HashMap<>();
        params.put("id", charge.getCarnet().getCarnetId().toString());
        params.put("parcel", charge.getParcel().toString());

        JSONObject body = new JSONObject();
        body.put("email", charge.getCliente().getEmail());

        return GNService.isOk(GNService.call(RESEND_PARCEL, params, body));
    }
}
