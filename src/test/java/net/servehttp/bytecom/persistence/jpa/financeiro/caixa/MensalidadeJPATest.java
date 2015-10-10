package net.servehttp.bytecom.persistence.jpa.financeiro.caixa;

import net.servehttp.bytecom.persistence.jpa.entity.financeiro.Mensalidade;
import net.servehttp.bytecom.persistence.jpa.financeiro.MensalidadeJPA;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.util.List;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by <a href="https://github.com/clairtonluz">clairtonluz</a> on 10/10/15.
 */
public class MensalidadeJPATest {

    private MensalidadeJPA jpa = new MensalidadeJPA();
    private EntityManager em = Persistence.createEntityManagerFactory("teste-pu").createEntityManager();

    @Before
    public void beforeEach() {
        assertNotNull(em);
        jpa.setEm(em);
    }

    @Test
    public void deveriaBuscarRegistroPorModalidadeENossoNumero() {
        List<Mensalidade> result = jpa.buscarTodosPorClienteDosUltimos6Meses(8);
        assertNotEquals(0, result.size());
    }

    @Test
    public void deveriaBuscarTodosPendentePorCliente() {
        List<Mensalidade> result = jpa.buscarTodosPendentePorCliente(8);
        assertNotEquals(0, result.size());
    }
}
