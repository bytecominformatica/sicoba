package net.servehttp.bytecom.model.jpa.financeiro;

import com.mysema.query.jpa.impl.JPAQuery;
import net.servehttp.bytecom.model.jpa.CrudJPA;
import net.servehttp.bytecom.model.jpa.entity.financeiro.retorno.Header;
import net.servehttp.bytecom.model.jpa.entity.financeiro.retorno.QHeader;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author clairton
 */
@Transactional
public class HeaderJPA extends CrudJPA {

    private static final long serialVersionUID = -5750421956273634462L;
    private QHeader h = QHeader.header;

    public Header buscarPorId(int id) {
        return new JPAQuery(entityManager).from(h).where(h.id.eq(id)).uniqueResult(h);
    }

    public List<Header> buscarTodosPorSequencial(int sequencial) {
        return new JPAQuery(entityManager).from(h).where(h.sequencial.eq(sequencial)).list(h);
    }
}
