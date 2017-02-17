package br.com.clairtonluz.sicoba.api.financeiro.gerencianet;

import br.com.clairtonluz.sicoba.model.entity.financeiro.gerencianet.carnet.Carnet;
import br.com.clairtonluz.sicoba.model.entity.financeiro.gerencianet.charge.Charge;
import br.com.clairtonluz.sicoba.service.financeiro.gerencianet.carnet.CarnetService;
import br.com.clairtonluz.sicoba.service.financeiro.gerencianet.charge.ChargeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by clairtonluz on 19/12/15.
 */
@RestController
@RequestMapping("api/carnets")
public class CarnetAPI {

    @Autowired
    private CarnetService carnetService;
    @Autowired
    private ChargeService chargeService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Carnet findById(@PathVariable Integer id) {
        return carnetService.findById(id);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Carnet> findByCliente(@RequestParam(value = "clienteId") Integer clienteId) {
        return carnetService.findByCliente(clienteId);
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public Carnet modelo(@RequestParam("clienteId") Integer clienteId) {
        return carnetService.createModelo(clienteId);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Carnet criar(@Valid @RequestBody Carnet carnet) {
        return carnetService.createCarnet(carnet);
    }

    @RequestMapping(value = "/{id}/parcels/{parcel}/resend", method = RequestMethod.POST)
    public void resendParcel(@PathVariable Integer id, @PathVariable Integer parcel) {
        carnetService.resendParcel(chargeService.findByCarnetAndParcel(id, parcel));
    }

    @RequestMapping(value = "/{id}/resend", method = RequestMethod.POST)
    public void resendCarnet(@PathVariable Integer id) {
        carnetService.resendCarnet(carnetService.findById(id));
    }

    @RequestMapping(value = "/{id}/metadata", method = RequestMethod.PUT)
    public void updateCarnetMetadata(@PathVariable Integer id) {
        carnetService.updateCarnetMetadata(carnetService.findById(id));
    }

    @RequestMapping(value = "/all/metadata", method = RequestMethod.PUT)
    public void updateCarnetMetadataAll() {
        carnetService.updateCarnetMetadataAll();
    }

    @RequestMapping(value = "/{id}/parcels/{parcel}", method = RequestMethod.PUT)
    public Charge updateParcelExpireAt(@PathVariable Integer id, @PathVariable Integer parcel, @Valid @RequestBody Charge charge) {
        return carnetService.updateParcelExpireAt(id, parcel, charge);
    }

    @RequestMapping(value = "/{id}/cancel", method = RequestMethod.PUT)
    public void cancel(@PathVariable Integer id) {
        carnetService.cancelCarnet(carnetService.findById(id));
    }

    @RequestMapping(value = "/{id}/parcels/{parcel}/cancel", method = RequestMethod.PUT)
    public void cancelParcel(@PathVariable Integer id, @PathVariable Integer parcel) {
        carnetService.cancelParcel(chargeService.findByCarnetAndParcel(id, parcel));
    }

}
