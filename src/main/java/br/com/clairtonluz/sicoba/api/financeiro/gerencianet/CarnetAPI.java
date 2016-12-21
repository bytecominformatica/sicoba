package br.com.clairtonluz.sicoba.api.financeiro.gerencianet;

import br.com.clairtonluz.sicoba.model.entity.financeiro.gerencianet.charge.Charge;
import br.com.clairtonluz.sicoba.model.pojo.financeiro.gerencianet.CarnetPojo;
import br.com.clairtonluz.sicoba.service.financeiro.gerencianet.carnet.CarnetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by clairtonluz on 19/12/15.
 */
@RestController
@RequestMapping("api/carnet")
public class CarnetAPI {
    @Autowired
    private CarnetService carnetService;

    @RequestMapping(value = "gerar", method = RequestMethod.POST)
    public List<Charge> gerar(@RequestBody CarnetPojo carnetPojo) {
        return carnetService.createCarnet(carnetPojo);
    }


}
