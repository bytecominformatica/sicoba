package br.com.clairtonluz.bytecom.controller.provedor;

import br.com.clairtonluz.bytecom.model.jpa.entity.provedor.impl.Mikrotik;
import br.com.clairtonluz.bytecom.model.service.provedor.MikrotikService;
import br.com.clairtonluz.bytecom.util.web.AlertaUtil;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

/**
 * @author clairton
 */
@Named
@ViewScoped
public class MikrotikController implements Serializable {

    private static final long serialVersionUID = -676355663109515972L;
    private List<Mikrotik> listMikrotik;

    private Mikrotik mikrotik;
    @Inject
    private MikrotikService mikrotikService;
    private String senha;

    @PostConstruct
    public void load() {
        consultar();
    }

    private void consultar() {
        listMikrotik = mikrotikService.findAll();
    }

    public void novo() {
        mikrotik = new Mikrotik();
    }

    public void limpar() {
        senha = null;
        mikrotik = null;
    }

    public void salvar() {
        if (senhaValida(mikrotik)) {
            mikrotikService.save(mikrotik);
            AlertaUtil.info("Salvo com sucesso!");
            limpar();
            consultar();
        }
    }

    private boolean senhaValida(Mikrotik m) {
        boolean valido = true;

        if (senha != null) {
            m.setPass(senha);
        }

        if (m.getId() == 0 && m.getPass() == null) {
            AlertaUtil.error("Digite uma senha");
            valido = false;
        }
        return valido;
    }

    public void remover() {
        mikrotikService.remove(mikrotik);
        limpar();
        consultar();
    }

    public Mikrotik getMikrotik() {
        return mikrotik;
    }

    public void setMikrotik(Mikrotik mikrotik) {
        this.mikrotik = mikrotik;
    }

    public List<Mikrotik> getListMikrotik() {
        return listMikrotik;
    }

    public void setListMikrotik(List<Mikrotik> listMikrotik) {
        this.listMikrotik = listMikrotik;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

}
