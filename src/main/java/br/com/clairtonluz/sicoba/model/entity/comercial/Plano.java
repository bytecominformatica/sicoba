package br.com.clairtonluz.sicoba.model.entity.comercial;

import br.com.clairtonluz.sicoba.model.entity.extra.BaseEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author clairton
 */
@Entity
@Table(name = "plano")
public class Plano extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "plano_id_seq")
    @SequenceGenerator(name = "plano_id_seq", sequenceName = "plano_id_seq")
    private Integer id;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
