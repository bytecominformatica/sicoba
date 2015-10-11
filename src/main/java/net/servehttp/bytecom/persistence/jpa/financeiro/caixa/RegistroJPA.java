package net.servehttp.bytecom.persistence.jpa.financeiro.caixa;

import com.mysema.query.jpa.impl.JPAQuery;
import net.servehttp.bytecom.persistence.jpa.CrudJPA;
import net.servehttp.bytecom.persistence.jpa.entity.financeiro.retorno.QRegistro;
import net.servehttp.bytecom.persistence.jpa.entity.financeiro.retorno.Registro;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

/**
 * Created by <a href="https://github.com/clairtonluz">Clairton Luz</a>
 */
@Transactional
public class RegistroJPA extends CrudJPA {

    private static final long serialVersionUID = -5750421956273634462L;
    @Inject
    private EntityManager em;
    private QRegistro registro = QRegistro.registro;

    @Override
    protected EntityManager getEm() {
        return em;
    }

    protected void setEm(EntityManager em) {
        this.em = em;
    }

    public Registro buscarPorModalidadeNossoNumero(Integer modalidade, Integer nossoNumero) {
        return new JPAQuery(em).from(registro).where(registro.nossoNumero.eq(nossoNumero)
                .and(registro.modalidadeNossoNumero.eq(modalidade))).uniqueResult(registro);
    }

}
