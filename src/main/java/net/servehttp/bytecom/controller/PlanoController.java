package net.servehttp.bytecom.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import net.servehttp.bytecom.controller.Util;
import net.servehttp.bytecom.persistence.GenericoJPA;
import net.servehttp.bytecom.persistence.entity.Contrato;
import net.servehttp.bytecom.persistence.entity.Plano;
import net.servehttp.bytecom.util.AlertaUtil;

/**
 * 
 * @author clairton
 */
@Named
@ViewScoped
public class PlanoController implements Serializable {

    private static final long serialVersionUID = 1L;
    private List<Plano> listPlanos;
    private Plano planoSelecionado;
    private Plano novoPlano = new Plano();
    @Inject
    private Util util;
    @Inject
    private GenericoJPA genericoJPA;

    @PostConstruct
    public void load() {
        listPlanos = genericoJPA.buscarTodos(Plano.class);
        limpar();
        getParameters();
    }

    private void getParameters() {
        String planoId = util.getParameters("planoId");
        if (planoId != null && !planoId.isEmpty()) {
            planoSelecionado = genericoJPA.buscarPorId(Plano.class, Integer.parseInt(planoId));
        }
    }

    public void setSelecionado(Plano plano) {
        planoSelecionado = plano;
    }

    private void limpar() {
        planoSelecionado = null;
    }

    public List<Plano> getListPlanos() {
        return listPlanos;
    }

    public void setListPlanos(List<Plano> listPlanos) {
        this.listPlanos = listPlanos;
    }

    public Plano getPlanoSelecionado() {
        return planoSelecionado;
    }

    public void setPlanoSelecionado(Plano planoSelecionado) {
        this.planoSelecionado = planoSelecionado;
    }

    public Plano getNovoPlano() {
        return novoPlano;
    }

    public void setNovoPlano(Plano novoPlano) {
        this.novoPlano = novoPlano;
    }

    public void salvar() {
        if (valida(novoPlano)) {
            genericoJPA.salvar(novoPlano);
            AlertaUtil.alerta("Plano adicionado com sucesso!");
            load();
            novoPlano = new Plano();
        }
    }

    private boolean valida(Plano plano) {
        boolean result = true;
        if (genericoJPA.buscarTodos("select p from Plano p where p.nome = ?1", plano.getNome(), Plano.class).isEmpty()) {
            result = false;
            AlertaUtil.alerta("Nome inválido!", AlertaUtil.ERROR);
        }
        return result;
    }

    public void atualizar() {
        genericoJPA.atualizar(planoSelecionado);
        load();
        AlertaUtil.alerta("Plano atualizado com sucesso!");
    }

    public String remover() {
        String page = null;
        List<Contrato> contratos = genericoJPA.buscarTodos("id",
                planoSelecionado.getId(), Contrato.class);
        if (contratos.isEmpty()) {
            genericoJPA.remover(planoSelecionado);
            load();
            AlertaUtil.alerta("Removido com sucesso!");
            page = "list";
        } else {
            AlertaUtil.alerta("Não é possível remover este plano, pois o mesmo está em uso!", AlertaUtil.ERROR);
        }
        return page;
    }

}
