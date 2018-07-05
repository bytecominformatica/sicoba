package br.com.clairtonluz.sicoba.service.financeiro.gerencianet.carnet;

import br.com.clairtonluz.sicoba.exception.ConflitException;
import br.com.clairtonluz.sicoba.model.entity.financeiro.gerencianet.GerencianetAccount;
import br.com.clairtonluz.sicoba.model.entity.financeiro.gerencianet.carnet.Carnet;
import br.com.clairtonluz.sicoba.model.entity.financeiro.gerencianet.charge.Charge;
import br.com.clairtonluz.sicoba.service.financeiro.gerencianet.GNService;
import br.com.clairtonluz.sicoba.util.DateUtil;
import br.com.clairtonluz.sicoba.util.StringUtil;
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
public class CarnetGNService {

    private static final String CREATE_CARNET = "createCarnet";
    private static final String DETAIL_CARNET = "detailCarnet";
    private static final String UPDATE_PARCEL = "updateParcel";
    private static final String UPDATE_CARNET_METADATA = "updateCarnetMetadata";
    private static final String CANCEL_CARNET = "cancelCarnet";
    private static final String CANCEL_PARCEL = "cancelParcel";
    private static final String RESEND_CARNET = "resendCarnet";
    private static final String RESEND_PARCEL = "resendParcel";
    private final GNService gnService;

    @Autowired
    public CarnetGNService(GNService gnService) {
        this.gnService = gnService;
    }

    JSONObject createCarnet(Carnet carnet) {
        GerencianetAccount account = carnet.getGerencianetAccount();
        JSONArray items = new JSONArray().put(GNService.createItem(carnet.getDescription(), carnet.getValue()));
        JSONObject customer = GNService.createConsumer(carnet.getCliente(), gnService.isNotifyClient(account));

        JSONObject configurations = GNService.createConfigurations(account);

        JSONObject body = new JSONObject();
        body.put("items", items);
        body.put("customer", customer);
        body.put("expire_at", carnet.getFirstPay()).toString();
        body.put("repeats", carnet.getRepeats());
        body.put("split_items", carnet.getSplitItems());
        body.put("configurations", configurations);
        body.put("metadata", GNService.createMetadata(gnService.createNotificationUrl(account)));

        return gnService.call(account, CREATE_CARNET, body);
    }

    boolean updateParcelExpireAt(Charge charge) {
        if (charge.getCarnet() == null || charge.getCarnet().getCarnetId() == null) {
            throw new ConflitException("Está cobrança não fas parte de um carnê");
        }
        Map<String, String> params = new HashMap<>();
        params.put("id", charge.getCarnet().getCarnetId().toString());
        params.put("parcel", charge.getParcel().toString());

        JSONObject body = new JSONObject();
        body.put("expire_at", charge.getExpireAt()).toString();

        return GNService.isOk(gnService.call(charge.getGerencianetAccount(), UPDATE_PARCEL, params, body));
    }

    boolean cancelCarnet(Carnet carnet) {
        Map<String, String> params = new HashMap<>();
        params.put("id", carnet.getCarnetId().toString());

        JSONObject response = gnService.call(carnet.getGerencianetAccount(), CANCEL_CARNET, params);
        return GNService.isOk(response);
    }

    public boolean cancelParcel(Charge charge) {

        if (charge.getCarnet() == null || charge.getCarnet().getCarnetId() == null) {
            throw new ConflitException("Está cobrança não fas parte de um carnê");
        }
        Map<String, String> params = new HashMap<>();
        params.put("id", charge.getCarnet().getCarnetId().toString());
        params.put("parcel", charge.getParcel().toString());

        JSONObject response = gnService.call(charge.getGerencianetAccount(), CANCEL_PARCEL, params);
        return GNService.isOk(response);
    }

    boolean updateCarnetMetadata(Carnet carnet) {
        GerencianetAccount account = carnet.getGerencianetAccount();
        Map<String, String> params = new HashMap<>();
        params.put("id", carnet.getCarnetId().toString());
        JSONObject body = GNService.createMetadata(gnService.createNotificationUrl(account), carnet.getId());
        JSONObject response = gnService.call(account, UPDATE_CARNET_METADATA, params, body);
        return GNService.isOk(response);
    }

    JSONObject detailCarnet(Carnet carnet) {
        Map<String, String> params = new HashMap<>();
        params.put("id", carnet.getCarnetId().toString());
        return gnService.call(carnet.getGerencianetAccount(), DETAIL_CARNET, params);
    }

    boolean resendCarnet(Carnet carnet) {
        if (StringUtil.isEmpty(carnet.getCliente().getEmail())) {
            throw new ConflitException("Cliente não possui email");
        }
        Map<String, String> params = new HashMap<>();
        params.put("id", carnet.getCarnetId().toString());

        JSONObject body = new JSONObject();
        body.put("email", carnet.getCliente().getEmail());

        return GNService.isOk(gnService.call(carnet.getGerencianetAccount(), RESEND_CARNET, params, body));
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

        return GNService.isOk(gnService.call(charge.getGerencianetAccount(), RESEND_PARCEL, params, body));
    }
}
