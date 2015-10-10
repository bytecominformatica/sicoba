package net.servehttp.bytecom.persistence.jpa.financeiro;

import java.io.Serializable;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import net.servehttp.bytecom.persistence.jpa.entity.financeiro.retorno.Header;
import net.servehttp.bytecom.persistence.jpa.entity.financeiro.retorno.QHeader;

import com.mysema.query.jpa.impl.JPAQuery;

/**
 * 
 * Created by <a href="https://github.com/clairtonluz">Clairton Luz</a>
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
