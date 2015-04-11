package net.servehttp.bytecom.persistence.jpa.provedor;

import java.util.List;

import net.servehttp.bytecom.persistence.jpa.entity.provedor.Mikrotik;
import net.servehttp.bytecom.persistence.jpa.entity.provedor.QMikrotik;
import net.servehttp.bytecom.persistence.jpa.extra.GenericoJPA;

import com.mysema.query.jpa.impl.JPAQuery;

public class MikrotikJPA extends GenericoJPA {

  private static final long serialVersionUID = -9176127497390577317L;

  private QMikrotik m = QMikrotik.mikrotik;

  public List<Mikrotik> buscarTodosMikrotik() {
    return new JPAQuery(em).from(m).orderBy(m.host.asc()).list(m);
  }

}
