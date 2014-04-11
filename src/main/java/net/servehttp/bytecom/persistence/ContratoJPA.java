package net.servehttp.bytecom.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import net.servehttp.bytecom.persistence.entity.Cliente;
import net.servehttp.bytecom.persistence.entity.Contrato;

/**
 *
 * @author clairton
 */
@Transactional
public class ContratoJPA {

    @PersistenceContext(unitName = "bytecom-pu")
    private EntityManager em;

    public Contrato buscarPorId(int id){
        return em.find(Contrato.class, id);
    }
    
    public void salvar(Contrato contrato) {
        em.persist(contrato);
    }
    
    public void atualizar(Contrato contrato) {
        em.merge(contrato);
    }
    
    public void remover(Contrato contrato) {
        em.remove(em.merge(contrato));
    }
    
    public List<Contrato> buscaTodosOsContratos(){
        return em.createQuery("select c from Contrato c order by c.id desc", Contrato.class).getResultList();
    }

    public List<Contrato> buscarContratoPorCliente(Cliente cliente) {
        return em.createQuery("select c from Contrato c where c.cliente.id = :clienteId", Contrato.class)
                .setParameter("clienteId", cliente.getId()).getResultList();
    }
}