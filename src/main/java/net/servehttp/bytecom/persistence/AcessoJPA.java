package net.servehttp.bytecom.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import net.servehttp.bytecom.persistence.entity.Acesso;
import net.servehttp.bytecom.persistence.entity.Cliente;

/**
 *
 * @author clairton
 */
@Transactional
public class AcessoJPA {

    @PersistenceContext(unitName = "bytecom-pu")
    private EntityManager em;

    public Acesso buscarPorId(int id) {
        return em.find(Acesso.class, id);
    }

    public void salvar(Acesso acesso) {
        em.persist(acesso);
    }

    public void atualizar(Acesso acesso) {
        em.merge(acesso);
    }

    public void remover(Acesso acesso) {
        em.remove(em.merge(acesso));
    }

    public List<Acesso> buscarAcessoPorCliente(Cliente cliente) {
        return em.createQuery("select a from Acesso a where a.cliente.id = :clienteId", Acesso.class)
                .setParameter("clienteId", cliente.getId()).getResultList();
    }

    /**
     * retorna o acesso referente ao IP passado como parametro.
     *
     * @param ip
     * @return Acesso
     */
    public Acesso buscaPorIp(String ip) {
        try {
            return em.createQuery("select a from Acesso a where a.ip = :ip", Acesso.class)
                    .setParameter("ip", ip)
                    .getSingleResult();
        } catch (javax.persistence.NoResultException e) {
            return null;
        }
    }

    /**
     * retorna o acesso referente ao MAC passado como parametro.
     *
     * @param mac
     * @return Acesso
     */
    public Acesso buscaPorMac(String mac) {
        try {
            return em.createQuery("select a from Acesso a where a.mac = :mac", Acesso.class)
                    .setParameter("mac", mac)
                    .getSingleResult();
        } catch (javax.persistence.NoResultException e) {
            return null;
        }
    }

    public List<Acesso> buscaTodos() {
        return em.createQuery("select a from Acesso a", Acesso.class).getResultList();
    }
}
