package net.servehttp.bytecom.persistence.jpa.financeiro.caixa;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

/**
 * Created by <a href="https://github.com/clairtonluz">clairtonluz</a> on 10/10/15.
 */
public class RegistroJPATest {

    private RegistroJPA jpa = new RegistroJPA();
    private EntityManager em = Persistence.createEntityManagerFactory("teste-pu").createEntityManager();

    @Before
    public void beforeEach() {
        assertNotNull(em);
        jpa.setEm(em);
    }

    @Test
    public void deveriaBuscarRegistroPorModalidadeENossoNumero() {
        jpa.buscarPorModalidadeNossoNumero(24, 120);
    }
}
