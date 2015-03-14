package net.servehttp.bytecom.comercial.jpa;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import net.servehttp.bytecom.comercial.jpa.entity.Cliente;
import net.servehttp.bytecom.comercial.jpa.entity.QCliente;
import net.servehttp.bytecom.comercial.jpa.entity.StatusCliente;
import net.servehttp.bytecom.extra.jpa.GenericoJPA;

import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.expr.BooleanExpression;

/**
 * 
 * @author clairton
 */
@Transactional
public class ClienteJPA extends GenericoJPA implements Serializable {

  private static final long serialVersionUID = 1857140370479772238L;
  private QCliente c = QCliente.cliente;

  public void setEntityManager(EntityManager em) {
    this.em = em;
  }

  public List<Cliente> buscarTodosClientePorNomeIp(String nome, String ip, StatusCliente status) {
    BooleanExpression condicao = c.id.eq(c.id);

    if (nome != null && !nome.trim().isEmpty()) {
      condicao = condicao.and(c.nome.like("%" + nome + "%"));
    }

    if (ip != null && !ip.isEmpty()) {
      condicao = condicao.and(c.acesso.ip.eq(ip));
    }

    if (status != null) {
      condicao = condicao.and(c.status.eq(status));
    }

    return new JPAQuery(em).from(c).where(condicao).orderBy(c.nome.asc()).limit(200).list(c);
  }

  public List<Cliente> buscaUltimosClientesAlterados() {
    LocalDateTime data = LocalDateTime.now().minusMonths(2);
    return new JPAQuery(em).from(c).where(c.updatedAt.gt(data)).orderBy(c.updatedAt.desc())
        .limit(20).list(c);
  }
}
