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
            Date date = DateUtil.INSTANCE.parse(dia + "/" + mes + "/" + ano);
            m.setDataVencimento(date);
            mensalidades.add(m);
        }
        em.persist(mensalidades);
        return mensalidades;
    }
}
