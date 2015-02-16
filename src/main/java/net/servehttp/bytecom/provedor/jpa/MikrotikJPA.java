package net.servehttp.bytecom.provedor.jpa;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.mysema.query.jpa.impl.JPAQuery;

import net.servehttp.bytecom.extra.jpa.GenericoJPA;
import net.servehttp.bytecom.provedor.jpa.entity.Mikrotik;
import net.servehttp.bytecom.provedor.jpa.entity.QMikrotik;

public class MikrotikJPA extends GenericoJPA {

  private static final long serialVersionUID = -9176127497390577317L;

  @Inject
  private EntityManager em;
  private QMikrotik m = QMikrotik.mikrotik;

  public List<Mikrotik> buscarTodosMikrotik() {
    return new JPAQuery(em).from(m).orderBy(m.host.asc()).list(m);
  }

}
