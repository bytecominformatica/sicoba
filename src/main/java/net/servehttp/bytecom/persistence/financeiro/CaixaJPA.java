package net.servehttp.bytecom.persistence.financeiro;

import java.io.Serializable;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import net.servehttp.bytecom.persistence.entity.caixa.Header;
import net.servehttp.bytecom.persistence.entity.caixa.QHeader;

import com.mysema.query.jpa.impl.JPAQuery;

/**
 * 
 * @author clairton
 */
@Transactional
public class CaixaJPA implements Serializable {

  private static final long serialVersionUID = 1857140370479772238L;
  @Inject
  private EntityManager em;

  public Header buscarHeaderPorSequencial(int sequencial) {
    QHeader h = QHeader.header;
    return new JPAQuery(em).from(h).where(h.sequencial.eq(sequencial)).uniqueResult(h);
  }

}
