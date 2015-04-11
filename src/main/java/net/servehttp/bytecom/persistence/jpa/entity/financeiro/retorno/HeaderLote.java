package net.servehttp.bytecom.persistence.jpa.entity.financeiro.retorno;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import net.servehttp.bytecom.persistence.jpa.entity.extra.EntityGeneric;
import net.servehttp.bytecom.util.converter.date.LocalDatePersistenceConverter;

@Entity
@Table(name = "header_lote")
public class HeaderLote extends EntityGeneric implements Serializable {

  private static final long serialVersionUID = 1073955464009876576L;

  @Column(name = "numero_remessa_retorno")
  private int numeroRemessaRetorno;
  @Column(name = "data_gravacao_remessa_retorno")
  @Convert(converter = LocalDatePersistenceConverter.class)
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
