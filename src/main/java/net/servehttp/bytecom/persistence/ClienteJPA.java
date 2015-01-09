package net.servehttp.bytecom.persistence;

import java.io.Serializable;
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

  public void setEntityManager(EntityManager em) {
    this.em = em;
  }

  public List<Cliente> buscaClientesPorNomeFoneEmailIp(String pesquisa) {
    QCliente c = QCliente.cliente;
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
    return em
        .createQuery("select c from Cliente c where c.updatedAt > :date order by c.updatedAt desc",
            Cliente.class).setParameter("date", DateUtil.incrementaMesAtual(-2)).setMaxResults(20)
        .getResultList();
  }
}
