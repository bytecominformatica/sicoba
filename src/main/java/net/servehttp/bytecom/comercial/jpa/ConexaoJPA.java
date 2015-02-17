package net.servehttp.bytecom.comercial.jpa;

import com.mysema.query.jpa.impl.JPAQuery;

import net.servehttp.bytecom.comercial.jpa.entity.Conexao;
import net.servehttp.bytecom.comercial.jpa.entity.QConexao;
import net.servehttp.bytecom.extra.jpa.GenericoJPA;

public class ConexaoJPA extends GenericoJPA {

  private static final long serialVersionUID = 556686224765753540L;
  QConexao c = QConexao.conexao;

  public boolean conexaoDisponivel(Conexao conexao) {
    Conexao conexao2 = buscarConexaoPorNome(conexao.getNome());
    return conexao2 == null || conexao2.getId() == conexao.getId();
  }

  public Conexao buscarConexaoPorNome(String nome) {
    return new JPAQuery(em).from(c).where(c.nome.eq(nome)).uniqueResult(c);
  }

}
