package br.com.clairtonluz.bytecom.api.financeiro;

import br.com.clairtonluz.bytecom.model.jpa.entity.comercial.Plano;
import br.com.clairtonluz.bytecom.model.jpa.entity.financeiro.retorno.Header;
import br.com.clairtonluz.bytecom.model.service.comercial.PlanoService;
import br.com.clairtonluz.bytecom.model.service.financeiro.RetornoCaixaService;
import br.com.clairtonluz.bytecom.pojo.financeiro.RetornoPojo;
import br.com.clairtonluz.bytecom.util.MensagemException;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import java.io.InputStream;
import java.util.List;

/**
 * Created by clairtonluz on 11/01/16.
 */
@Path("retornos")
public class RetornoAPI {

    @Inject
    private PlanoService planoService;

    @Inject
    private RetornoCaixaService retornoCaixaService;

    @GET
    public List<Plano> query() {
        return planoService.buscarTodos();
    }

    @POST
    @Path("/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public List<RetornoPojo> upload(MultipartFormDataInput input) throws Exception {
        List<RetornoPojo> retornoPojos = null;

        List<InputPart> parts = input.getFormDataMap().get("file");

        for (int i = 0; i < parts.size(); i++) {
            InputPart inputPart = parts.get(i);
            String filename = getFileName(inputPart.getHeaders());
            InputStream in = inputPart.getBody(InputStream.class, null);

            Header header = retornoCaixaService.parse(in, filename);
            retornoPojos = retornoCaixaService.processarHeader(header);
        }

        return retornoPojos;
    }

    private boolean isFileValid(String filename) throws MensagemException {
        boolean valid = true;
        if (!filename.toLowerCase().contains(".ret")) {
            valid = false;
            throw new MensagemException("Tipo de arquivo inválido");
        }
        return valid;
    }

    private String getFileName(MultivaluedMap<String, String> header) {

        String[] contentDisposition = header.getFirst("Content-Disposition").split(";");

        for (String filename : contentDisposition) {
            if ((filename.trim().startsWith("filename"))) {

                String[] name = filename.split("=");

                String finalFileName = name[1].trim().replaceAll("\"", "");
                return finalFileName;
            }
        }
        return "unknown";
    }
}
