package br.com.clairtonluz.sicoba.service.financeiro.gerencianet;

import br.com.clairtonluz.sicoba.exception.BadRequestException;
import br.com.clairtonluz.sicoba.exception.ConflitException;
import br.com.clairtonluz.sicoba.model.entity.comercial.Cliente;
import br.com.clairtonluz.sicoba.model.entity.comercial.Contrato;
import br.com.clairtonluz.sicoba.model.entity.comercial.Endereco;
import br.com.clairtonluz.sicoba.model.entity.financeiro.Titulo;
import br.com.clairtonluz.sicoba.model.entity.financeiro.gerencianet.Credentials;
import br.com.clairtonluz.sicoba.model.pojo.financeiro.gerencianet.Carnet;
import br.com.clairtonluz.sicoba.service.comercial.ContratoService;
import br.com.clairtonluz.sicoba.service.financeiro.TituloService;
import br.com.clairtonluz.sicoba.util.DateUtil;
import br.com.clairtonluz.sicoba.util.StringUtil;
import br.com.gerencianet.gnsdk.Gerencianet;
import br.com.gerencianet.gnsdk.exceptions.GerencianetException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by clairton on 09/11/16.
 */
@Service
public class CarnetService {

    @Autowired
    private TituloService tituloService;
    @Autowired
    private ContratoService contratoService;


    public JSONObject criarCarne(Contrato contrato, Date vencimento, Double valor, Double desconto, Integer quantidadeParcela) {
         /* *********  Set credentials parameters ******** */
        Credentials credentials = new Credentials();

        JSONObject options = credentials.getOptions();
        String item = String.format("Internet Banda Larga %s", contrato.getPlano().getNome());
        JSONArray items = getItens(item, valor);
        JSONObject customer = getConsumer(contrato.getCliente());
        JSONArray instructions = getInstructions(desconto);

        JSONObject body = new JSONObject();
        body.put("items", items);
        body.put("customer", customer);
        body.put("expire_at", DateUtil.formatISO(vencimento));
        body.put("repeats", quantidadeParcela);
        body.put("split_items", false);
        body.put("instructions", instructions); // opcional

        try {
            Gerencianet gn = new Gerencianet(options);
            JSONObject response = gn.call("createCarnet", new HashMap<>(), body);
            System.out.println(response);
            return response;
        } catch (GerencianetException e) {
            throw new ConflitException(String.format("%d - %s", e.getCode(), e.getErrorDescription()));
        } catch (Exception e) {
            throw new ConflitException(e.getMessage());
        }
    }

    private JSONArray getInstructions(Double desconto) {
        JSONArray instructions = new JSONArray();
        instructions.put("Não receber após 60 dias do vencimento");
        instructions.put("Após o vencimento cobrar juros de 0.033% por dia");
        instructions.put("Após o vencimento cobrar multa de 5%");
        if (desconto != null && desconto > 0) {
            instructions.put(String.format("Até o dia do vencimento conceder desconto de R$%s", StringUtil.formatCurrence(desconto)));
        }
        return instructions;
    }

    private JSONObject getConsumer(Cliente cliente) {
        JSONObject customer = new JSONObject();
        Endereco endereco = cliente.getEndereco();
        if (endereco != null) {
            JSONObject customerAddres = new JSONObject();
            customerAddres.put("street", endereco);
            customerAddres.put("number", endereco.getNumero());
            customerAddres.put("neighborhood", endereco.getBairro().getNome());
            customerAddres.put("zipcode", endereco.getCep());
            customerAddres.put("city", endereco.getBairro().getCidade().getNome());
            customerAddres.put("complement", endereco.getComplemento());
            customerAddres.put("state", endereco.getBairro().getCidade().getEstado().getUf());
            customer.put("address", customerAddres); // opcional
        }

        if (StringUtil.isCnpj(cliente.getCpfCnpj())) {
            JSONObject juridicalPerson = new JSONObject();
            juridicalPerson.put("corporate_name", cliente.getNome());
            juridicalPerson.put("cnpj", cliente.getCpfCnpj());
            customer.put("juridical_person", juridicalPerson);
        } else {
            customer.put("name", cliente.getNome());
            customer.put("cpf", cliente.getCpfCnpj());
        }
        customer.put("phone_number", cliente.getFoneContato());
        customer.put("email", "clairton.luz@bytecominformatica.com.br"); // opcional
        return customer;
    }

    private JSONArray getItens(String item, Double valor) {
        JSONArray items = new JSONArray();

        JSONObject item1 = new JSONObject();
        item1.put("name", item);
//        item1.put("amount", 1); // não é obrigatório
        item1.put("value", valor * 100);

        items.put(item1);
        return items;
    }


    @Transactional
    public List<Titulo> criarCarnet(Carnet carnet) {
        List<Titulo> titulos = tituloService.criarTitulos(carnet);
        Contrato contrato = contratoService.buscarPorCliente(carnet.getClienteId());
        JSONObject resultJson = criarCarne(contrato, carnet.getDataInicio(), carnet.getValor(), carnet.getDesconto(), carnet.getQuantidadeParcela());

        if (resultJson.getInt("code") == HttpStatus.OK.value()) {
            JSONObject carnetJson = resultJson.getJSONObject("data");
            JSONArray charges = carnetJson.getJSONArray("charges");

            if (titulos.size() != charges.length())
                throw new ConflitException(String.format("A quantidade de titulo não bate com a quantidade de cobraças geradas: %d titulo(s) gerados e %d cobrancas geradas", titulos.size(), charges.length()));

            for (int i = 0; i < charges.length(); i++) {
                JSONObject it = charges.getJSONObject(i);
                Titulo titulo = titulos.get(i);
                titulo.setCarnetId(carnetJson.getInt("carnet_id"));
                titulo.setCarnetUrl(carnetJson.getString("link"));
                titulo.setChargeId(it.getInt("charge_id"));
                titulo.setChargeUrl(it.getString("url"));
                titulo.setParcel(it.getInt("parcel"));
                Date vencimento = DateUtil.parseDate(it.getString("expire_at"));
                titulo.setDataVencimento(vencimento);
            }

            tituloService.save(titulos);
        } else {
            throw new BadRequestException(resultJson.toString());
        }
        return titulos;
    }
}
