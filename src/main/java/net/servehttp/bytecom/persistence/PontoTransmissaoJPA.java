package net.servehttp.bytecom.persistence;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import com.mysema.query.jpa.impl.JPAQuery;

import net.servehttp.bytecom.persistence.entity.pingtest.PontoTransmissao;
import net.servehttp.bytecom.persistence.entity.pingtest.PontoTransmissaoPojo;
import net.servehttp.bytecom.persistence.entity.pingtest.QPontoTransmissao;

/**
 *
 * @author clairton
 */
@Transactional
public class PontoTransmissaoJPA implements Serializable {

  private static final long serialVersionUID = 5006498719314183836L;
  @PersistenceContext(unitName = "bytecom-pu")
  private EntityManager em;
  private QPontoTransmissao p = QPontoTransmissao.pontoTransmissao;

  public void setEntityManager(EntityManager em) {
    this.em = em;
  }

  public List<PontoTransmissaoPojo> buscarTodosPontoTransmissaoDetachERecebeDeNull() {
    List<PontoTransmissao> list = new JPAQuery(em).from(p).where(p.recebeDe.isNotNull()).list(p);
    return converteParaPojo(list);
  }

  private List<PontoTransmissaoPojo> converteParaPojo(List<PontoTransmissao> list) {
    List<PontoTransmissaoPojo> listPojo = new ArrayList<>();
    for (PontoTransmissao pt : list) {
      PontoTransmissaoPojo ptp = new PontoTransmissaoPojo();
      ptp.setIp1(pt.getIp1());
      ptp.setIp2(pt.getIp2());
      ptp.setIp3(pt.getIp3());
      ptp.setIp4(pt.getIp4());
      if (pt.getTransmitePara() != null && !pt.getTransmitePara().isEmpty()) {
        ptp.setTransmitePara(converteParaPojo(pt.getTransmitePara()));
      }
      listPojo.add(ptp);
    }
    return listPojo;
  }

}
