package br.com.clairtonluz.sicoba.model.entity.financeiro.edi.retorno;

import br.com.clairtonluz.sicoba.model.entity.extra.BaseEntity;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "header_lote")
public class HeaderLote extends BaseEntity {
    @Column(name = "numero_remessa_retorno")
    private int numeroRemessaRetorno;
    @Column(name = "data_gravacao_remessa_retorno")
    private LocalDate dataGravacaoRemessaRetorno;

    @ManyToOne
    @JoinColumn(name = "header_id")
    private Header header;
    @OneToMany(mappedBy = "headerLote", cascade = CascadeType.ALL)
    private List<Registro> registros;
    @OneToOne(mappedBy = "headerLote", cascade = CascadeType.ALL)
    private TrailerLote trailerLote;

    public int getNumeroRemessaRetorno() {
        return numeroRemessaRetorno;
    }

    public void setNumeroRemessaRetorno(int numeroRemessaRetorno) {
        this.numeroRemessaRetorno = numeroRemessaRetorno;
    }

    public LocalDate getDataGravacaoRemessaRetorno() {
        return dataGravacaoRemessaRetorno;
    }

    public void setDataGravacaoRemessaRetorno(LocalDate dataGravacaoRemessaRetorno) {
        this.dataGravacaoRemessaRetorno = dataGravacaoRemessaRetorno;
    }

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public List<Registro> getRegistros() {
        return registros;
    }

    public void setRegistros(List<Registro> registros) {
        this.registros = registros;
    }

    public TrailerLote getTrailerLote() {
        return trailerLote;
    }

    public void setTrailerLote(TrailerLote trailerLote) {
        this.trailerLote = trailerLote;
    }
}
