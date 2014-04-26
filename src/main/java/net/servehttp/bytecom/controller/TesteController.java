package net.servehttp.bytecom.controller;

import java.io.Serializable;

import javassist.runtime.Inner;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.google.gson.Gson;

import net.servehttp.bytecom.pojo.EnderecoPojo;
import net.servehttp.bytecom.util.AlertaUtil;
import net.servehttp.bytecom.util.EnderecoUtil;

/**
 * 
 * @author clairton
 * 
 */
@ManagedBean
@ViewScoped
public class TesteController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7972159878826621995L;
	private EnderecoPojo endereco = new EnderecoPojo();

	public void buscar() {
		endereco = EnderecoUtil.INSTANCE.getEndereco(endereco.getCep());
		if (endereco.getLocalidade() == null || endereco.getLocalidade().isEmpty()) {
			AlertaUtil.alerta("CEP não encontrado!");
		}
	}

	public EnderecoPojo getEndereco() {
		return endereco;
	}

	public void setEndereco(EnderecoPojo endereco) {
		this.endereco = endereco;
	}

}
