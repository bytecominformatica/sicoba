package br.com.clairtonluz.bytecom.model.jpa.financeiro;

import br.com.clairtonluz.bytecom.model.jpa.entity.financeiro.Titulo;
import br.com.clairtonluz.bytecom.model.jpa.entity.financeiro.StatusTitulo;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Transactional
public class TituloRelatorioJPA implements Serializable {

    private static final long serialVersionUID = -666959135258997285L;
    @Inject
    protected EntityManager em;

    public List<Titulo> buscarPorDataStatus(LocalDate inicio, LocalDate fim, StatusTitulo status, boolean buscarPorDataOcorrencia) {

        String jpql;
        if (buscarPorDataOcorrencia) {
            jpql = "select m from Titulo m where m.dataOcorrencia between :inicio and :fim ";
        } else {
            jpql = "select m from Titulo m where m.dataVencimento between :inicio and :fim ";
        }

        if (status != null) {
            jpql += "and m.status = :status ";
        }

        if (buscarPorDataOcorrencia) {
            jpql += "order by m.dataOcorrencia, m.dataVencimento desc ";
        } else {
            jpql += "order by m.dataVencimento, m.dataOcorrencia desc ";
        }

        TypedQuery<Titulo> query =
                em.createQuery(jpql, Titulo.class).setParameter("inicio", inicio)
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
