package br.com.clairtonluz.bytecom.model.jpa.extra;

import br.com.clairtonluz.bytecom.model.jpa.CrudJPA;
import br.com.clairtonluz.bytecom.model.jpa.entity.extra.ClienteGeoReferencia;
import br.com.clairtonluz.bytecom.model.jpa.entity.extra.QClienteGeoReferencia;
import com.mysema.query.jpa.impl.JPAQuery;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

/**
 * @author Felipe W. M. Martins
 */
@Transactional
public class ClienteGeoReferenciaJPA extends CrudJPA {

    private static final long serialVersionUID = 7468802847947425443L;

    private QClienteGeoReferencia cg = QClienteGeoReferencia.clienteGeoReferencia;
    @Inject
    private EntityManager entityManager;

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager em) {
        this.entityManager = em;
    }

    public ClienteGeoReferencia findByClient(int clientId) {
        return new JPAQuery(entityManager).from(cg).where(cg.cliente.id.eq(clientId)).uniqueResult(cg);
    }

}
