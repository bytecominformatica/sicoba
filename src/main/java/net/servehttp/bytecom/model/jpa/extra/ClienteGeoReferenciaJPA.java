package net.servehttp.bytecom.model.jpa.extra;

import com.mysema.query.jpa.impl.JPAQuery;
import net.servehttp.bytecom.model.jpa.entity.comercial.Cliente;
import net.servehttp.bytecom.model.jpa.entity.extra.ClienteGeoReferencia;
import net.servehttp.bytecom.model.jpa.entity.extra.QClienteGeoReferencia;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.io.Serializable;

/**
 * @author Felipe W. M. Martins
 */
@Transactional
public class ClienteGeoReferenciaJPA implements Serializable {

    private static final long serialVersionUID = 7468802847947425443L;
    @Inject
    protected EntityManager em;
    private QClienteGeoReferencia cg = QClienteGeoReferencia.clienteGeoReferencia;

    public void setEntityManager(EntityManager em) {
        this.em = em;
    }

    public ClienteGeoReferencia buscarClienteGeoReferenciaPorCliente(Cliente cliente) {
        return new JPAQuery(em).from(cg).where(cg.cliente.id.eq(cliente.getId())).uniqueResult(cg);
    }


}
