package br.com.clairtonluz.bytecom.model.jpa.comercial;

import br.com.clairtonluz.bytecom.model.jpa.CrudJPA;
import br.com.clairtonluz.bytecom.model.jpa.entity.comercial.Cliente;
import br.com.clairtonluz.bytecom.model.jpa.entity.comercial.QCliente;
import br.com.clairtonluz.bytecom.model.jpa.entity.comercial.StatusCliente;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.expr.BooleanExpression;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author clairton
 */
@Transactional
public class ClienteJPA extends CrudJPA {

    private static final long serialVersionUID = 1857140370479772238L;
    private QCliente c = QCliente.cliente;

    @Inject
    private EntityManager entityManager;

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager em) {
        this.entityManager = em;
    }

    public List<Cliente> buscarTodosClientePorNomeIp(String nome, String ip, String mac, StatusCliente status) {
        BooleanExpression condicao = c.id.eq(c.id);

        if (nome != null && !nome.trim().isEmpty()) {
            condicao = condicao.and(c.nome.like("%" + nome + "%"));
        }

        if (ip != null && !ip.isEmpty()) {
            condicao = condicao.and(c.conexao.ip.eq(ip));
        }

        if (mac != null && !mac.isEmpty()) {
            condicao = condicao.and(
                    (c.contrato.equipamento.mac.like("%" + mac + "%")
                            .or(c.contrato.equipamentoWifi.mac.like("%" + mac + "%")))
            );
        }

        if (status != null) {
            condicao = condicao.and(c.status.eq(status));
        }

        return new JPAQuery(entityManager).from(c).where(condicao).orderBy(c.nome.asc()).limit(200).list(c);
    }

    public List<Cliente> buscaUltimosClientesAlterados() {
        LocalDateTime data = LocalDateTime.now().minusMonths(2);
        return new JPAQuery(entityManager).from(c).where(c.updatedAt.gt(data)).orderBy(c.updatedAt.desc())
                .limit(20).list(c);
    }

    public Cliente buscarPorId(int clienteId) {
        return entityManager.find(Cliente.class, clienteId);
    }

    public Cliente findByRg(String rg) {
        Cliente cliente = null;
        if (rg != null) {
            cliente = new JPAQuery(entityManager).from(c).where(c.rg.eq(rg)).uniqueResult(c);
        }
        return cliente;
    }

    public Cliente findByCpfCnpj(String cpfCnpj) {
        Cliente cliente = null;
        if (cpfCnpj != null) {
            cliente = new JPAQuery(entityManager).from(c).where(c.cpfCnpj.eq(cpfCnpj)).uniqueResult(c);
        }
        return cliente;
    }

    public Cliente findByEmail(String email) {
        Cliente cliente = null;
        if (email != null) {
            cliente = new JPAQuery(entityManager).from(c).where(c.email.eq(email)).uniqueResult(c);
        }
        return cliente;
    }

    public List<Cliente> findAll() {
        return new JPAQuery(entityManager).from(c).orderBy(c.nome.asc()).list(c);
    }
}
