package br.com.clairtonluz.sicoba.model.entity.comercial;

import br.com.clairtonluz.sicoba.model.entity.extra.EntityGeneric;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * @author clairton
 */
@Entity
@Table(name = "plano")
public class Plano extends EntityGeneric {

    @NotNull(message = "nome é obrigatório")
    private String nome;
    @NotNull(message = "Upload é obrigatório")
    private Integer upload;
    @NotNull(message = "Download é obrigatório")
    private Integer download;
    @Column(name = "valor_instalacao")
    private Double valorInstalacao;
    @Column(name = "valor_mensalidade")
    private Double valorTitulo;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getUpload() {
        return upload;
    }

    public void setUpload(Integer upload) {
        this.upload = upload;
    }

    public Integer getDownload() {
        return download;
    }

    public void setDownload(Integer download) {
        this.download = download;
    }

    public Double getValorInstalacao() {
        return valorInstalacao;
    }

    public void setValorInstalacao(Double valorInstalacao) {
        this.valorInstalacao = valorInstalacao;
    }

    public Double getValorTitulo() {
        return valorTitulo;
    }

    public void setValorTitulo(Double valorTitulo) {
        this.valorTitulo = valorTitulo;
    }
}
