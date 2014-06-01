package net.servehttp.bytecom.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import net.servehttp.bytecom.persistence.AcessoJPA;
import net.servehttp.bytecom.persistence.GenericoJPA;
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
@Named
@ViewScoped
public class AcessoController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7972159878826621995L;
	private List<Acesso> listAcessos;
	// private Acesso acesso;
	private Cliente cliente;
	@Inject
	private AcessoJPA acessoJPA;
	@Inject
	GenericoJPA genericoJPA;
	@Inject
	private Util util;

	@PostConstruct
	public void load() {
		String clienteId = util.getParameters("clienteId");

		if (clienteId != null && !clienteId.isEmpty()) {
			if ((cliente = genericoJPA.buscarPorId(Cliente.class, Integer.parseInt(clienteId))) != null) {
				if ((cliente.getAcesso()) == null) {
					cliente.setAcesso(new Acesso());
					cliente.getAcesso().setCliente(cliente);

					cliente.getAcesso().setIp("192.168.33.2");
					cliente.getAcesso().setMascara("255.255.255.0");
					cliente.getAcesso().setGateway("192.168.33.1");
					cliente.getAcesso().setStatus(Acesso.ATIVO);
					Contrato c = cliente.getContrato();
					if (c != null) {
						Equipamento e = c.getEquipamento();
						if (e != null) {
							cliente.getAcesso().setMac(e.getMac());
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

	public String salvar() {
		genericoJPA.salvar(cliente);
		load();
		AlertaUtil.alerta("Acesso adicionado com sucesso!");
		return "edit";
	}

	public String atualizar() {
		String page = null;

		if (isDisponivel(cliente.getAcesso())) {
			if (cliente.getAcesso().getId() > 0) {
				genericoJPA.atualizar(cliente.getAcesso());
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
		genericoJPA.remover(cliente.getAcesso());
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

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

}
