package net.servehttp.bytecom.pojo;

/**
 * @author Clairton Luz
 * 
 */
public class EnderecoPojo {
	private String localidade;
	private String cep;
	private String logradouro;
	private String bairro;
	private String uf;

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public String getLocalidade() {
		return localidade;
	}

	public void setLocalidade(String localidade) {
		this.localidade = localidade;
	}

	@Override
	public String toString() {
		return "EnderecoPojo [localidade=" + localidade + ", cep=" + cep
				+ ", logradouro=" + logradouro + ", bairro=" + bairro + ", uf="
				+ uf + "]";
	}

	
}
