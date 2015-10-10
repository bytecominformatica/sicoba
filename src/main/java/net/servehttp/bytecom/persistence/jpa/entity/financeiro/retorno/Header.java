package net.servehttp.bytecom.persistence.jpa.entity.financeiro.retorno;

import net.servehttp.bytecom.persistence.jpa.entity.extra.EntityGeneric;
import net.servehttp.bytecom.util.converter.date.LocalDateTimePersistenceConverter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "header")
public class Header extends EntityGeneric implements Serializable {

    private static final long serialVersionUID = -7418368665896060578L;
    private int sequencial;

    @Column(name = "nome_arquivo")
    private String nomeArquivo;
    @Column(name = "data_geracao")
    @Convert(converter = LocalDateTimePersistenceConverter.class)
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
