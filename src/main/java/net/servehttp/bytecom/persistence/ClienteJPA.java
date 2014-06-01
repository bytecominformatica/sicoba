package net.servehttp.bytecom.persistence;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import net.servehttp.bytecom.persistence.entity.Cliente;
import net.servehttp.bytecom.util.DateUtil;

/**
 * 
 * @author clairton
 */
@Transactional
public class ClienteJPA implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1857140370479772238L;
  @PersistenceContext(unitName = "bytecom-pu")
  private EntityManager em;

  public void setEntityManager(EntityManager em) {
    this.em = em;
  }

  public List<Cliente> buscaClientesPorNomeFoneEmail(String nome, String fone, String email) {

    List<Cliente> list;

    String jpql = "select c from Cliente c where 1 = 1";
    boolean buscar = false;

    if (nome != null && !nome.isEmpty()) {
      jpql += " and c.nome like :nome";
      buscar = true;
    }
    if (fone != null && !fone.isEmpty()) {
      jpql += " and (c.foneTitular = :fone or c.foneContato = :fone)";
      buscar = true;
    }
    if (email != null && !email.isEmpty()) {
      jpql += " and c.email= :email";
      buscar = true;
    }

    jpql += " order by c.nome";

    if (buscar) {
      TypedQuery<Cliente> query = em.createQuery(jpql, Cliente.class);

      if (nome != null && !nome.isEmpty()) {
        query.setParameter("nome", "%" + nome + "%");
      }
      if (fone != null && !fone.isEmpty()) {
        query.setParameter("fone", fone);
      }
      if (email != null && !email.isEmpty()) {
        query.setParameter("email", email);
      }

      list = query.getResultList();
    } else {
      list = new ArrayList<>();
    }
    return list;
  }

  public List<Cliente> buscarClientePorNome(String pesquisa) {
    return em.createQuery("select c from Cliente c where c.nome like :nome", Cliente.class)
        .setParameter("nome", pesquisa + "%").getResultList();
  }


  public Cliente buscarClientePorEmail(String email) {
    try {
      return em.createQuery("select c from Cliente c where c.email = :email", Cliente.class)
          .setParameter("email", email).getSingleResult();
    } catch (javax.persistence.NoResultException e) {
      return null;
    }
  }

  public List<Cliente> buscaClientePorStatus(int status) {
    return em
        .createQuery(
            "select c from Cliente c inner join c.acesso a where a.status = :status order by c.nome",
            Cliente.class).setParameter("status", status).getResultList();

  }

  public List<Cliente> buscaUltimosClientesAlterados() {
    return em
        .createQuery("select c from Cliente c where c.updateAt > :date order by c.updateAt desc",
            Cliente.class).setParameter("date", DateUtil.incrementaMesAtual(-2)).setMaxResults(20)
        .getResultList();
  }
}
