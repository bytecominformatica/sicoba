package net.servehttp.bytecom.comercial.jpa;

import net.servehttp.bytecom.comercial.jpa.entity.Conexao;
import net.servehttp.bytecom.comercial.jpa.entity.QConexao;
import net.servehttp.bytecom.extra.jpa.GenericoJPA;

import com.mysema.query.jpa.impl.JPAQuery;

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
  
  public String getIpLivre() {
    String rede = "10.77.3.";
    String ipLivre = null;
    for (int i = 10; i <= 250; i++) {
      Conexao result = new JPAQuery(em).from(c).where(c.ip.eq(rede + i)).uniqueResult(c);
      if (result == null) {
        ipLivre = rede + i;
        break;
      }
    }
    System.out.println("IP = " + ipLivre);

    return ipLivre;
  }

}
