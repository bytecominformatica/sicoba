package br.com.clairtonluz.sicoba.api.financeiro.gerencianet;

import br.com.clairtonluz.sicoba.model.entity.financeiro.gerencianet.charge.Charge;
import br.com.clairtonluz.sicoba.service.financeiro.gerencianet.charge.ChargeService;
import br.com.clairtonluz.sicoba.util.SendEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by clairtonluz on 19/12/15.
 */
@RestController
@RequestMapping("api/charges")
public class ChargeAPI {

    @Autowired
    private ChargeService chargeService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Charge> findByClienteOrCarnet(@RequestParam("clienteId") Integer clienteId,
                                              @RequestParam(value = "carnetId", required = false) Integer carnetId) {
        if (carnetId != null) {
            return chargeService.findByCarnet(carnetId);
        } else {
            return chargeService.findByCliente(clienteId);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Charge findById(@PathVariable Integer id) {
        return chargeService.findById(id);
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public Charge modelo(@RequestParam("clienteId") Integer clienteId) {
        return chargeService.createModelo(clienteId);
    }

    @RequestMapping(value = "/overdue", method = RequestMethod.GET)
    public List<Charge> overdue() {
        return chargeService.overdue();
    }

    @RequestMapping(method = RequestMethod.POST)
    public Charge create(@Valid @RequestBody Charge charge) {
        return chargeService.createCharge(charge);
    }

    @RequestMapping(value = "/{id}/pay", method = RequestMethod.POST)
    public Charge createBankingBillet(@PathVariable Integer id) {
        return chargeService.setPaymentToBankingBillet(chargeService.findById(id));
    }

    @RequestMapping(value = "/{id}/link", method = RequestMethod.POST)
    public Charge createPaymentLink(@PathVariable Integer id) {
        return chargeService.createPaymentLink(chargeService.findById(id));
    }

    @RequestMapping(value = "/{id}/billet/resend", method = RequestMethod.POST)
    public void resendBillet(@PathVariable Integer id) {
        chargeService.resendBillet(chargeService.findById(id));
    }

    @RequestMapping(value = "/{id}/cancel", method = RequestMethod.PUT)
    public Charge cancel(@PathVariable Integer id) {
        return chargeService.cancelCharge(chargeService.findById(id));
    }

    @RequestMapping(value = "/{id}/manualpayment", method = RequestMethod.PUT)
    public Charge manualPayment(@PathVariable Integer id, @RequestBody Charge charge) {
        return chargeService.manualPayment(charge);
    }

    @RequestMapping(value = "/{id}/billet", method = RequestMethod.PUT)
    public Charge updateExpireAt(@PathVariable Integer id, @Valid @RequestBody Charge charge) {
        return chargeService.updateBilletExpireAt(charge);
    }

    @RequestMapping(value = "/{id}/metadata", method = RequestMethod.PUT)
    public void updateChargeMetadata(@PathVariable Integer id) {
        chargeService.updateChargeMetadata(chargeService.findById(id));
    }

    @RequestMapping(value = "/all/metadata", method = RequestMethod.PUT)
    public void updateCarnetMetadataAll() {
        chargeService.updateCarnetMetadataAll();
    }

}
