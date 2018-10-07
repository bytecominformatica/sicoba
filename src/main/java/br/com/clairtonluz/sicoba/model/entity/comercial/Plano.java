package br.com.clairtonluz.sicoba.model.entity.comercial;

import br.com.clairtonluz.sicoba.model.entity.extra.BaseEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author clairton
 */
@Entity
@Table(name = "plano")
public class Plano extends BaseEntity {

    public static final String PLANO_INATIVO = "INATIVO";
    @NotNull(message = "nome é obrigatório")
    private String nome;
    @NotNull(message = "Upload é obrigatório")
    private Integer upload;
    @NotNull(message = "Download é obrigatório")
    private Integer download;
    @Column(name = "valor_instalacao")
    private Double valorInstalacao;
    private Double valor;

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

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
