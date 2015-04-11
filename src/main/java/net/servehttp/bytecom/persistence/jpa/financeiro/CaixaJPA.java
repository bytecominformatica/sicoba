package net.servehttp.bytecom.persistence.jpa.financeiro;

import javax.transaction.Transactional;

import net.servehttp.bytecom.persistence.jpa.entity.financeiro.retorno.Header;
import net.servehttp.bytecom.persistence.jpa.entity.financeiro.retorno.QHeader;
import net.servehttp.bytecom.persistence.jpa.extra.GenericoJPA;

import com.mysema.query.jpa.impl.JPAQuery;

/**
 * 
 * @author clairton
 */
@Transactional
public class CaixaJPA extends GenericoJPA {

  private static final long serialVersionUID = 1857140370479772238L;

  public Header buscarHeaderPorSequencial(int sequencial) {
    QHeader h = QHeader.header;
    return new JPAQuery(em).from(h).where(h.sequencial.eq(sequencial)).uniqueResult(h);
  }

}
