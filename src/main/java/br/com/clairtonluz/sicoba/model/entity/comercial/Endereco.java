package br.com.clairtonluz.sicoba.model.entity.comercial;

import br.com.clairtonluz.sicoba.model.entity.extra.BaseEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "endereco")
public class Endereco extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "endereco_id_seq")
    @SequenceGenerator(name = "endereco_id_seq", sequenceName = "endereco_id_seq")
    private Integer id;
    @NotNull(message = "cep é obrigatório")
    private String cep;
    @NotNull(message = "logradouro é obrigatório")
    private String logradouro;
    private String numero;
    private String complemento;

    @NotNull(message = "bairro é obrigatório")
    @ManyToOne
    private Bairro bairro;

    public String getComplemento() {
        return this.complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getLogradouro() {
        return this.logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero != null && !numero.isEmpty() ? numero : "S/N";
    }

    public Bairro getBairro() {
        return this.bairro;
    }

    public void setBairro(Bairro bairro) {
        this.bairro = bairro;
    }

    public String getLogradouroNumeroComplemento() {
        StringBuilder sb = new StringBuilder();
        sb.append(logradouro).append(", ").append(numero);
        if (complemento != null && !complemento.isEmpty()) {
            sb.append(", ").append(complemento);
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(logradouro).append(", ").append(numero);
        if (complemento != null && !complemento.isEmpty()) {
            sb.append(", ").append(complemento);
        }
        if (bairro != null) {
            sb.append(", ").append(bairro.getNome()).append(", ").append(bairro.getCidade())
                    .append(" - ").append(bairro.getCidade().getEstado().getUf());
        }
        return sb.toString();
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
