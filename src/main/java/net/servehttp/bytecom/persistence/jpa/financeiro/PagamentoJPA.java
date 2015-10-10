package net.servehttp.bytecom.persistence.jpa.financeiro;

import com.mysema.query.jpa.impl.JPADeleteClause;
import com.mysema.query.jpa.impl.JPAQuery;
import net.servehttp.bytecom.persistence.jpa.CrudJPA;
import net.servehttp.bytecom.persistence.jpa.entity.financeiro.Cedente;
import net.servehttp.bytecom.persistence.jpa.entity.financeiro.Mensalidade;
import net.servehttp.bytecom.persistence.jpa.entity.financeiro.QCedente;
import net.servehttp.bytecom.persistence.jpa.entity.financeiro.QMensalidade;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by <a href="https://github.com/clairtonluz">Clairton Luz</a>
 */
@Transactional
public class PagamentoJPA extends CrudJPA {

    private static final long serialVersionUID = -5750421956273634462L;
    @Inject
    protected EntityManager em;

    public void setEntityManager(EntityManager em) {
        this.em = em;
    }

    @Override
    protected EntityManager getEm() {
        return em;
    }
}
