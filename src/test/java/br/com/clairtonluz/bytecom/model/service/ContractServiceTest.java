package br.com.clairtonluz.bytecom.model.service;

import br.com.clairtonluz.bytecom.model.jpa.comercial.ContratoJPA;
import br.com.clairtonluz.bytecom.model.service.comercial.ContractService;
import br.com.clairtonluz.bytecom.util.CreateEntityManager;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by clairtonluz on 14/11/15.
 */
public class ContractServiceTest {

    private ContractService contractService;

    @Before
    public void setup() {
        ContratoJPA contratoJPA = new ContratoJPA(CreateEntityManager.INSTANCE.getEntityManager());
        contractService = new ContractService(contratoJPA);
    }

    @Test
    public void deveria_buscar_todas_instalacoes_por_mes() {
        assertEquals(contractService.findAllInstalledThisMonth().size(), 1);
    }

    @Test
    public void deveria_buscar_contrato_por_id() {
        assertNotNull(contractService.findById(1));
    }
}
