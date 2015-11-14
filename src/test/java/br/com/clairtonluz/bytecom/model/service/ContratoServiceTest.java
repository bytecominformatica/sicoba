package br.com.clairtonluz.bytecom.model.service;

import br.com.clairtonluz.bytecom.model.jpa.comercial.ContratoJPA;
import br.com.clairtonluz.bytecom.model.service.comercial.ContratoService;
import br.com.clairtonluz.bytecom.util.CreateEntityManager;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by clairtonluz on 14/11/15.
 */
public class ContratoServiceTest {

    private ContratoService contratoService;

    @Before
    public void setup() {
        ContratoJPA contratoJPA = new ContratoJPA(CreateEntityManager.INSTANCE.getEntityManager());
        contratoService = new ContratoService(contratoJPA);
    }

    @Test
    public void deveria_buscar_todas_instalacoes_por_mes() {
        assertEquals(contratoService.buscarTodosInstaladoEsseMes().size(), 1);
    }

    @Test
    public void deveria_buscar_contrato_por_id() {
        assertNotNull(contratoService.buscarPorId(1));
    }
}
