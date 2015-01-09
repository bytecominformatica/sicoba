package net.servehttp.bytecom.persistence;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import net.servehttp.bytecom.persistence.entity.cadastro.Cliente;
import net.servehttp.bytecom.persistence.entity.cadastro.QCliente;

import com.mysema.query.jpa.impl.JPAQuery;
import com.servehttp.bytecom.commons.DateUtil;

/**
 * 
 * @author clairton
 */
@Transactional
public class ClienteJPA implements Serializable {

  private static final long serialVersionUID = 1857140370479772238L;
  @PersistenceContext(unitName = "bytecom-pu")
  private EntityManager em;
  private QCliente c = QCliente.cliente;

  public void setEntityManager(EntityManager em) {
    this.em = em;
  }

  public List<Cliente> buscaClientesPorNomeFoneEmailIp(String pesquisa) {
    return new JPAQuery(em)
        .from(c)
        .where(
            c.nome.like("%" + pesquisa + "%").or(
                c.foneTitular.eq(pesquisa).or(
                    c.foneContato.eq(pesquisa)
                        .or(c.email.eq(pesquisa).or(c.acesso.ip.eq(pesquisa))))))
        .orderBy(c.nome.asc()).list(c);
  }

  public List<Cliente> buscaUltimosClientesAlterados() {
    Calendar data = DateUtil.incrementaMesAtual(-2);
    return new JPAQuery(em).from(c).where(c.updatedAt.gt(data)).orderBy(c.updatedAt.desc())
        .limit(20).list(c);
  }
}
