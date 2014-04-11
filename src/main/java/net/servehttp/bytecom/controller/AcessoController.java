package net.servehttp.bytecom.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import net.servehttp.bytecom.persistence.AcessoJPA;
import net.servehttp.bytecom.persistence.ClienteJPA;
import net.servehttp.bytecom.persistence.entity.Acesso;
import net.servehttp.bytecom.persistence.entity.Cliente;
import net.servehttp.bytecom.persistence.entity.Contrato;
import net.servehttp.bytecom.persistence.entity.Equipamento;
import net.servehttp.bytecom.util.AlertaUtil;

/**
 * 
 * @author clairton
 * 
 */
@ManagedBean
@ViewScoped
public class AcessoController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7972159878826621995L;
	private List<Acesso> listAcessos;
	private Acesso acesso;
	private Cliente cliente;
	@Inject
	private ClienteJPA clienteJPA;
	@Inject
	private AcessoJPA acessoJPA;
	@Inject
	private Util util;

	@PostConstruct
	public void load() {
		String clienteId = util.getParameters("clienteId");

		if (clienteId != null && !clienteId.isEmpty()) {
			if ((cliente = clienteJPA.buscarPorId(Integer.parseInt(clienteId))) != null) {
				if ((acesso = cliente.getAcesso()) == null) {
					acesso = new Acesso();
					acesso.setCliente(cliente);
					cliente.setAcesso(acesso);

					acesso.setIp("192.168.33.2");
					acesso.setMascara("255.255.255.0");
					acesso.setGateway("192.168.33.1");
					acesso.setStatus(Acesso.ATIVO);
					Contrato c = acesso.getCliente().getContrato();
					if (c != null) {
						Equipamento e = c.getEquipamento();
						if (e != null) {
							acesso.setMac(e.getMac());
						}
					}
				}
			}
		}
	}

	public List<Acesso> getListAcessos() {
		return listAcessos;
	}

	public void setListAcessos(List<Acesso> listAcessos) {
		this.listAcessos = listAcessos;
	}

	public Acesso getNovoAcesso() {
		return acesso;
	}

	public void setNovoAcesso(Acesso novoAcesso) {
		this.acesso = novoAcesso;
	}

	public String salvar() {
		acessoJPA.salvar(acesso);
		load();
		AlertaUtil.alerta("Acesso adicionado com sucesso!");
		return "edit";
	}

	public String atualizar() {
		String page = null;

		if (isDisponivel(acesso)) {
			if (acesso.getId() > 0) {
				acessoJPA.atualizar(acesso);
				load();
				AlertaUtil.alerta("Acesso atualizado com sucesso!");
				page = "edit";
			} else {
				page = salvar();
			}
		}
		return page;
	}

	public void remover() {
		acessoJPA.remover(acesso);
		load();
		AlertaUtil.alerta("Acesso removido com sucesso!");
	}

	private boolean isDisponivel(Acesso acesso) {
		boolean disponivel = true;
		Acesso a = acessoJPA.buscaPorIp(acesso.getIp());
		if (a != null && acesso.getId() != a.getId()) {
			AlertaUtil.alerta("IP já Cadastrado", AlertaUtil.ERROR);
			disponivel = false;
		}

		a = acessoJPA.buscaPorMac(acesso.getMac());

		if (a != null && acesso.getId() != a.getId()) {
			AlertaUtil.alerta("MAC já Cadastrado", AlertaUtil.ERROR);
			disponivel = false;
		}

		return disponivel;
	}

	public Acesso getAcesso() {
		return acesso;
	}

	public void setAcesso(Acesso acesso) {
		this.acesso = acesso;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

}
