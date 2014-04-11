package net.servehttp.bytecom.persistence;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import net.servehttp.bytecom.persistence.entity.Cliente;
import net.servehttp.bytecom.persistence.entity.Contrato;
import net.servehttp.bytecom.persistence.entity.Mensalidade;
import net.servehttp.bytecom.util.DateUtil;

/**
 *
 * @author clairton
 */
@Transactional
public class MensalidadeJPA {

    @PersistenceContext(unitName = "bytecom-pu")
    private EntityManager em;

    public Mensalidade buscarPorId(int id) {
        return em.find(Mensalidade.class, id);
    }

    public void salvar(Mensalidade mensalidade) {
        em.persist(mensalidade);
    }

    public void salvar(List<Mensalidade> mensalidades) {
        for (Mensalidade m : mensalidades) {
            em.persist(m);
        }
    }

    public void atualizar(Mensalidade mensalidade) {
        em.merge(mensalidade);
    }

    public void remover(Mensalidade mensalidade) {
        em.remove(em.merge(mensalidade));
    }

    public List<Mensalidade> getAllMensalidades() {
        return em.createQuery("select m from Mensalidade m", Mensalidade.class).getResultList();
    }

    public List<Mensalidade> buscarMensalidadePorCliente(Cliente cliente) {
        return em.createQuery("select m from Mensalidade m where m.cliente.id = :clienteId order by m.dataVencimento desc", Mensalidade.class)
                .setParameter("clienteId", cliente.getId()).setMaxResults(12).getResultList();
    }

    /**
     * retorna as mensalidades por status.
     *
     * @param status
     * @return Mensalidade
     */
    public Mensalidade buscarMensalidadesPorStatus(int status) {
        try {
            return em.createQuery("select m from Mensalidade m where m.status = :status", Mensalidade.class)
                    .setParameter("status", status)
                    .getSingleResult();
        } catch (javax.persistence.NoResultException e) {
            return null;
        }
    }

    public List<Mensalidade> buscarMensalidadesPorClienteEVencimento(Cliente cliente, Date vencimento) {
        try {
            return em.createQuery("select m from Mensalidade m inner join m.cliente c where m.cliente.id = :clienteId and m.dataVencimento = :vencimento", Mensalidade.class)
                    .setParameter("vencimento", vencimento)
                    .setParameter("clienteId", cliente.getId())
                    .getResultList();
        } catch (javax.persistence.NoResultException e) {
            return null;
        }
    }

    /**
     *
     * @param mes int 0 - 11
     * @param ano int
     * @return List< Mensalidade >
     */
    public List<Mensalidade> buscarMensalidadePorMesAno(int mes, int ano) {
        Date inicio = DateUtil.getDate(1, mes, ano);
        Date fim = DateUtil.getUltimoDiaDoMes(inicio);
        return em.createQuery("select m from Mensalidade m where m.dataVencimento between :inicio and :fim", Mensalidade.class)
                .setParameter("inicio", inicio)
                .setParameter("fim", fim)
                .getResultList();
    }

    /**
     * gera a mensalidade dos clientes
     *
     * @param mes int 0 - 11
     * @param ano int
     * @param clientes List < Cliente >
     * @return List< Mensalidade>
     */
    public List<Mensalidade> gerarMensalidadePorMesAno(int mes, int ano, List<Cliente> clientes) {
        List<Mensalidade> mensalidades = new ArrayList<>();
        ++mes;
        for (Cliente c : clientes) {
            Contrato contrato = c.getContrato();
            Mensalidade m = new Mensalidade();
            m.setCliente(c);
            m.setStatus(Mensalidade.NAO_PAGA);
            m.setValor(contrato.getPlano().getValorMensalidade());
            int dia = contrato.getVencimento();
            Date date = DateUtil.parse(dia + "/" + mes + "/" + ano);
            m.setDataVencimento(date);
            mensalidades.add(m);
        }
        salvar(mensalidades);
        return mensalidades;
    }
}
