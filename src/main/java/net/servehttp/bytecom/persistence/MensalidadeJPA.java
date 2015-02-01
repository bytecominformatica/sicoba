package net.servehttp.bytecom.persistence;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import net.servehttp.bytecom.persistence.entity.cadastro.Mensalidade;
import net.servehttp.bytecom.persistence.entity.cadastro.QMensalidade;
import net.servehttp.bytecom.persistence.entity.financeiro.boleto.Cedente;
import net.servehttp.bytecom.persistence.entity.financeiro.boleto.QCedente;

import com.mysema.query.jpa.impl.JPADeleteClause;
import com.mysema.query.jpa.impl.JPAQuery;

/**
 *
 * @author clairton
 */
@Transactional
public class MensalidadeJPA {

  @Inject
  private EntityManager em;
  private QMensalidade m = QMensalidade.mensalidade;

  public void remover(Mensalidade mensalidade) {
    new JPADeleteClause(em, m).where(m.id.eq(mensalidade.getId())).execute();
  }

  public void removerPorBoleto(int inicio, int fim) {
    new JPADeleteClause(em, m).where(m.numeroBoleto.between(inicio, fim)).execute();
  }

  public void setEntityManager(EntityManager em) {
    this.em = em;
  }

  public List<Mensalidade> buscarMensalidadesPorBoletos(int numeroBoletoInicio, int numeroBoletoFim) {
    return new JPAQuery(em).from(m)
        .where(m.numeroBoleto.between(numeroBoletoInicio, numeroBoletoFim)).list(m);
  }

  public Cedente buscarCedente() {
    QCedente c = QCedente.cedente;
    return new JPAQuery(em).from(c).uniqueResult(c);
  }
}
