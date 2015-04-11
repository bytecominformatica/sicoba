package net.servehttp.bytecom.provedor.jpa;

import java.util.List;

import net.servehttp.bytecom.persistence.jpa.extra.GenericoJPA;
import net.servehttp.bytecom.provedor.jpa.entity.Mikrotik;
import net.servehttp.bytecom.provedor.jpa.entity.QMikrotik;

import com.mysema.query.jpa.impl.JPAQuery;

public class MikrotikJPA extends GenericoJPA {

  private static final long serialVersionUID = -9176127497390577317L;

  private QMikrotik m = QMikrotik.mikrotik;

  public List<Mikrotik> buscarTodosMikrotik() {
    return new JPAQuery(em).from(m).orderBy(m.host.asc()).list(m);
  }

}
