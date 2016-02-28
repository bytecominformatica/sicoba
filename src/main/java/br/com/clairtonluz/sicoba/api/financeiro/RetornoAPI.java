package br.com.clairtonluz.sicoba.api.financeiro;

import br.com.clairtonluz.sicoba.model.pojo.financeiro.RetornoPojo;
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

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public List<RetornoPojo> upload(@RequestParam("name") String name,
                                    @RequestParam("file") MultipartFile file) throws Exception {
        List<RetornoPojo> retornoPojos = new ArrayList<>();
        if (!file.isEmpty()) {
            byte[] bytes = file.getBytes();
            BufferedOutputStream stream =
                    new BufferedOutputStream(new FileOutputStream(new File(name)));
            stream.write(bytes);
            stream.close();

        } else {
            throw new RuntimeException("Arquivo vazio");
        }
        return retornoPojos;
    }
}
