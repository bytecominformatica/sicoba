package br.com.clairtonluz.bytecom.model.jpa.financeiro;

import br.com.clairtonluz.bytecom.model.jpa.entity.financeiro.Mensalidade;
import br.com.clairtonluz.bytecom.model.jpa.entity.financeiro.StatusMensalidade;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Transactional
public class MensalidadeRelatorioJPA implements Serializable {

    private static final long serialVersionUID = -666959135258997285L;
    @Inject
    protected EntityManager em;

    public List<Mensalidade> buscarPorDataStatus(LocalDate inicio, LocalDate fim, StatusMensalidade status, boolean buscarPorDataOcorrencia) {

        String jpql;
        if (buscarPorDataOcorrencia) {
            jpql = "select m from Mensalidade m where m.dataOcorrencia between :inicio and :fim ";
        } else {
            jpql = "select m from Mensalidade m where m.dataVencimento between :inicio and :fim ";
        }

        if (status != null) {
            jpql += "and m.status = :status ";
        }

        if (buscarPorDataOcorrencia) {
            jpql += "order by m.dataOcorrencia, m.dataVencimento desc ";
        } else {
            jpql += "order by m.dataVencimento, m.dataOcorrencia desc ";
        }

        TypedQuery<Mensalidade> query =
                em.createQuery(jpql, Mensalidade.class).setParameter("inicio", inicio)
                        .setParameter("fim", fim);

        if (status != null) {
            query.setParameter("status", status);
        }

        return query.getResultList();
    }

    public void setEntityManager(EntityManager em2) {
        this.em = em2;
    }
}
