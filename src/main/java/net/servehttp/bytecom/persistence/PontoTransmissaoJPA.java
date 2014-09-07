package net.servehttp.bytecom.persistence;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import net.servehttp.bytecom.persistence.entity.pingtest.PontoTransmissao;
import net.servehttp.bytecom.persistence.entity.pingtest.PontoTransmissaoPojo;

/**
 *
 * @author clairton
 */
@Transactional
public class PontoTransmissaoJPA implements Serializable {

  private static final long serialVersionUID = 5006498719314183836L;
  @PersistenceContext(unitName = "bytecom-pu")
  private EntityManager em;

  public void setEntityManager(EntityManager em) {
    this.em = em;
  }

  public List<PontoTransmissaoPojo> buscarTodosPontoTransmissaoDetachERecebeDeNull() {
    List<PontoTransmissao> list =
        em.createQuery("select pt from PontoTransmissao pt where pt.recebeDe is null",
            PontoTransmissao.class).getResultList();
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
