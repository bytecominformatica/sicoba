package br.com.clairtonluz.bytecom.model.service.dashboard;

import br.com.clairtonluz.bytecom.model.jpa.dashboard.DashboadJPA;

import javax.inject.Inject;
import java.io.Serializable;

/**
 * Created by clairtonluz on 14/11/15.
 */
public class DashboardService implements Serializable {
    private static final long serialVersionUID = 1L;

    @Inject
    private DashboadJPA dashboadJPA;

    public long getQuantidadeInstalacoes() {
        return dashboadJPA.getQuantidadeInstalacoesDoMes();
    }
}
