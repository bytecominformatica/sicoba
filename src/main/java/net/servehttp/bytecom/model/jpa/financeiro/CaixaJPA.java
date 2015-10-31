package net.servehttp.bytecom.model.jpa.financeiro;

import com.mysema.query.jpa.impl.JPAQuery;
import net.servehttp.bytecom.model.jpa.entity.financeiro.retorno.Header;
import net.servehttp.bytecom.model.jpa.entity.financeiro.retorno.QHeader;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.io.Serializable;

/**
 * @author clairton
 */
@Transactional
public class CaixaJPA implements Serializable {

    private static final long serialVersionUID = 1857140370479772238L;
    @Inject
    protected EntityManager em;

    public Header buscarHeaderPorSequencial(int sequencial) {
        QHeader h = QHeader.header;
        return new JPAQuery(em).from(h).where(h.sequencial.eq(sequencial)).uniqueResult(h);
    }

}
