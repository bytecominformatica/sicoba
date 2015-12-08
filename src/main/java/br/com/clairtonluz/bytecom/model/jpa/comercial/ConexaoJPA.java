package br.com.clairtonluz.bytecom.model.jpa.comercial;

import br.com.clairtonluz.bytecom.model.jpa.CrudJPA;
import br.com.clairtonluz.bytecom.model.jpa.entity.comercial.Conexao;
import br.com.clairtonluz.bytecom.model.jpa.entity.comercial.QConexao;
import com.mysema.query.jpa.impl.JPAQuery;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

public class ConexaoJPA extends CrudJPA {

    private static final long serialVersionUID = 556686224765753540L;

    QConexao c = QConexao.conexao;
    @Inject
    private EntityManager entityManager;

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager em) {
        this.entityManager = em;
    }

    public boolean conexaoDisponivel(Conexao conexao) {
        Conexao conexao2 = buscarConexaoPorNome(conexao.getNome());
        return conexao2 == null || conexao2.getId() == conexao.getId();
    }

    public Conexao buscarConexaoPorNome(String nome) {
        return new JPAQuery(entityManager).from(c).where(c.nome.eq(nome)).uniqueResult(c);
    }

    public String getIpLivre() {
        String rede = "10.77.3.";
        String ipLivre = null;
        for (int i = 10; i <= 250; i++) {
            Conexao result = new JPAQuery(entityManager).from(c).where(c.ip.eq(rede + i)).uniqueResult(c);
            if (result == null) {
                ipLivre = rede + i;
                break;
            }
        }
        return ipLivre;
    }

    public List<Conexao> buscarTodos() {
        return new JPAQuery(entityManager).from(c).orderBy(c.nome.asc()).list(c);
    }
}
