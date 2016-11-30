package br.com.clairtonluz.sicoba.service.financeiro;

import br.com.clairtonluz.sicoba.model.entity.financeiro.gerencianet.Credentials;
import br.com.clairtonluz.sicoba.util.StringUtil;
import br.com.gerencianet.gnsdk.Gerencianet;
import br.com.gerencianet.gnsdk.exceptions.GerencianetException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * Created by clairton on 09/11/16.
 */
@Service
public class BoletoService {

    public static void main(String... args) {

        new BoletoService().criarCarne();
    }


    public void criarCarne() {
         /* *********  Set credentials parameters ******** */
        Credentials credentials = new Credentials();

        JSONObject options = new JSONObject();
        options.put("client_id", credentials.getClientId());
        options.put("client_secret", credentials.getClientSecret());
        options.put("sandbox", credentials.isSandbox());

		/* ************************************************* */

        JSONArray items = new JSONArray();


        JSONObject item1 = new JSONObject();
        item1.put("name", "Internet Banda Larga 1MB");
        item1.put("amount", 1);
        item1.put("value", 4000);

        items.put(item1);

        JSONObject customerAddres = new JSONObject();
        customerAddres.put("street", "Rua 23 de maio");
        customerAddres.put("number", "S/N");
        customerAddres.put("neighborhood", "Patrícia Gomes");
        customerAddres.put("zipcode", "61607040");
        customerAddres.put("city", "Caucaia");
        customerAddres.put("complement", "Altos");
        customerAddres.put("state", "CE");

        JSONObject customer = new JSONObject();

        JSONObject juridicalPerson = new JSONObject();
        juridicalPerson.put("corporate_name", "Lorenzo e Stella Pizzaria Ltda");
        juridicalPerson.put("cnpj", "58438851000109");
        customer.put("juridical_person", juridicalPerson);
//        customer.put("name", "Francisco Cesar");
//        customer.put("cpf", "04267484171");
        customer.put("phone_number", "5144916523");
        customer.put("email", "clairton.luz@bytecominformatica.com.br"); // opcional
        customer.put("address", customerAddres); // opcional

        JSONArray instructions = new JSONArray();
        instructions.put("Não receber após 60 dias do vencimento");
        instructions.put("Após o vencimento cobrar juros de 0.033% por dia");
        instructions.put("Após o vencimento cobrar multa de 5%");
        instructions.put("Até o dia do vencimento conceder desconto de R$5,00");

        JSONObject body = new JSONObject();
        body.put("items", items);
        body.put("customer", customer);
        body.put("expire_at", "2016-12-20");
        body.put("repeats", 6);
        body.put("split_items", false);
        body.put("instructions", instructions); // opcional

        try {
            Gerencianet gn = new Gerencianet(options);
            JSONObject response = gn.call("createCarnet", new HashMap<String, String>(), body);
            System.out.println(response);
        } catch (GerencianetException e) {
            System.out.println(e.getCode());
            System.out.println(e.getError());
            System.out.println(e.getErrorDescription());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
