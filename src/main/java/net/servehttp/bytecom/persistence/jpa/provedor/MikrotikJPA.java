package net.servehttp.bytecom.persistence.jpa.provedor;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import net.servehttp.bytecom.persistence.jpa.entity.provedor.impl.Mikrotik;
import net.servehttp.bytecom.persistence.jpa.entity.provedor.impl.QMikrotik;

import com.mysema.query.jpa.impl.JPAQuery;

public class MikrotikJPA implements Serializable {

  private static final long serialVersionUID = -9176127497390577317L;
  @Inject
  protected EntityManager em;

  private QMikrotik m = QMikrotik.mikrotik;

  public List<Mikrotik> buscarTodosMikrotik() {
    return new JPAQuery(em).from(m).orderBy(m.host.asc()).list(m);
  }

}
