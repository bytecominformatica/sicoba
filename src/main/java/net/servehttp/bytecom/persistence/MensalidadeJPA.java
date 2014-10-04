package net.servehttp.bytecom.persistence;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import net.servehttp.bytecom.persistence.entity.cadastro.Cliente;
import net.servehttp.bytecom.persistence.entity.cadastro.Contrato;
import net.servehttp.bytecom.persistence.entity.cadastro.Mensalidade;
import net.servehttp.bytecom.persistence.entity.cadastro.StatusMensalidade;

import com.servehttp.bytecom.commons.DateUtil;

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
      m.setStatus(StatusMensalidade.PENDENTE);
      m.setValor(contrato.getPlano().getValorMensalidade());
      int dia = contrato.getVencimento();
      Date date = DateUtil.parse(dia + "/" + mes + "/" + ano);
      m.setDataVencimento(date);
      mensalidades.add(m);
    }
    em.persist(mensalidades);
    return mensalidades;
  }

  public void remover(Mensalidade m) {
    em.createQuery("delete from Mensalidade m where m.id  = :id").setParameter("id", m.getId())
        .executeUpdate();
  }

  public void setEntityManager(EntityManager em) {
    this.em = em;
  }

  public List<Mensalidade> buscarMensalidadesPorBoletos(int numeroBoletoInicio, int numeroBoletoFim) {
    return em
        .createQuery("select m from Mensalidade m where m.numeroBoleto between :inicio and :fim ",
            Mensalidade.class).setParameter("inicio", numeroBoletoInicio)
        .setParameter("fim", numeroBoletoFim).getResultList();
  }
}
