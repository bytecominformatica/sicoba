package br.com.clairtonluz.sicoba.model.entity.financeiro.edi.retorno;

import br.com.clairtonluz.sicoba.model.entity.extra.BaseEntity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "header")
public class Header extends BaseEntity {

    private int sequencial;

    @Column(name = "nome_arquivo")
    private String nomeArquivo;
    @Column(name = "data_geracao")
    private LocalDateTime dataGeracao;

    @OneToMany(mappedBy = "header", cascade = CascadeType.ALL)
    private List<HeaderLote> headerLotes;
    @OneToOne(mappedBy = "header", cascade = CascadeType.ALL)
    private Trailer trailer;

    public String getNomeArquivo() {
        return nomeArquivo;
    }

    public void setNomeArquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }

    public int getSequencial() {
        return sequencial;
    }

    public void setSequencial(int sequencial) {
        this.sequencial = sequencial;
    }

    public LocalDateTime getDataGeracao() {
        return dataGeracao;
    }

    public void setDataGeracao(LocalDateTime dataGeracao) {
        this.dataGeracao = dataGeracao;
    }

    public Trailer getTrailer() {
        return trailer;
    }

    public void setTrailer(Trailer trailer) {
        this.trailer = trailer;
    }

    public List<HeaderLote> getHeaderLotes() {
        return headerLotes;
    }

    public void setHeaderLotes(List<HeaderLote> headerLotes) {
        this.headerLotes = headerLotes;
    }

}
