package net.servehttp.bytecom.util;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import com.google.gson.Gson;

import net.servehttp.bytecom.pojo.EnderecoPojo;

public enum EnderecoUtil {

	INSTANCE;

	public EnderecoPojo getEndereco(String cep) {
		String path = "http://cep.correiocontrol.com.br/" + cep + ".json";

		Client client = ClientBuilder.newClient();
		String resultado = client.target(path).request("text/plain")
				.get(String.class);


		EnderecoPojo e;
		if(resultado.contains("\"erro\":true")){
			e = new EnderecoPojo();
			e.setCep(cep);
			return e;
		}


		Gson g = new Gson();

		e = g.fromJson(resultado, EnderecoPojo.class);
		return e;
	}

}