package br.com.clairtonluz.bytecom.model.jpa.financeiro;

import br.com.clairtonluz.bytecom.model.jpa.entity.financeiro.retorno.Header;
import br.com.clairtonluz.bytecom.model.jpa.entity.financeiro.retorno.QHeader;
import com.mysema.query.jpa.impl.JPAQuery;
import br.com.clairtonluz.bytecom.model.jpa.CrudJPA;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

/**
 * @author clairton
 */
@Transactional
public class HeaderJPA extends CrudJPA {

    private static final long serialVersionUID = -5750421956273634462L;
    private QHeader h = QHeader.header;
    @Inject
    private EntityManager entityManager;

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager em) {
        this.entityManager = em;
    }
    public Header buscarPorId(int id) {
        return new JPAQuery(entityManager).from(h).where(h.id.eq(id)).uniqueResult(h);
    }

    public List<Header> buscarTodosPorSequencial(int sequencial) {
        return new JPAQuery(entityManager).from(h).where(h.sequencial.eq(sequencial)).list(h);
    }
}
