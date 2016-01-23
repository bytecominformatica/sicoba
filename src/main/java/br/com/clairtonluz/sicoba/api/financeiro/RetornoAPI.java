package br.com.clairtonluz.sicoba.api.financeiro;

import br.com.clairtonluz.sicoba.model.entity.comercial.Plano;
import br.com.clairtonluz.sicoba.model.pojo.financeiro.RetornoPojo;
import br.com.clairtonluz.sicoba.model.service.comercial.PlanoService;
import br.com.clairtonluz.sicoba.model.service.financeiro.RetornoCaixaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by clairtonluz on 11/01/16.
 */
@RestController
@RequestMapping("api/retornos")
public class RetornoAPI {

    @Autowired
    private PlanoService planoService;

    @Autowired
    private RetornoCaixaService retornoCaixaService;

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<Plano> query() {
        return planoService.buscarTodos();
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public List<RetornoPojo> upload(@RequestParam("name") String name,
                                    @RequestParam("file") MultipartFile file) throws Exception {
        List<RetornoPojo> retornoPojos = new ArrayList<>();
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                BufferedOutputStream stream =
                        new BufferedOutputStream(new FileOutputStream(new File(name)));
                stream.write(bytes);
                stream.close();
//                return "You successfully uploaded " + name + "!";
            } catch (Exception e) {
//                return "You failed to upload " + name + " => " + e.getMessage();
            }
        } else {
//            return "You failed to upload " + name + " because the file was empty.";
        }
//
//        List<InputPart> parts = input.getFormDataMap().get("file");
//
//        for (int i = 0; i < parts.size(); i++) {
//            InputPart inputPart = parts.get(i);
//            String filename = getFileName(inputPart.getHeaders());
//            InputStream in = inputPart.getBody(InputStream.class, null);
//
//            Header header = retornoCaixaService.parse(in, filename);
//            retornoPojos = retornoCaixaService.processarHeader(header);
//        }

        return retornoPojos;
    }

    private boolean isFileValid(String filename) {
        boolean valid = true;
        if (!filename.toLowerCase().contains(".ret")) {
            valid = false;
//            TODO: MensagemException
//            throw new MensagemException("Tipo de arquivo inválido");
        }
        return valid;
    }

//    private String getFileName(MultivaluedMap<String, String> header) {
//
//        String[] contentDisposition = header.getFirst("Content-Disposition").split(";");
//
//        for (String filename : contentDisposition) {
//            if ((filename.trim().startsWith("filename"))) {
//
//                String[] name = filename.split("=");
//
//                String finalFileName = name[1].trim().replaceAll("\"", "");
//                return finalFileName;
//            }
//        }
//        return "unknown";
//    }
}
