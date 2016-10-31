package br.com.clairtonluz.sicoba.model.entity.financeiro.edi.retorno;

import br.com.clairtonluz.sicoba.model.entity.extra.BaseEntity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "header")
public class Header extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "header_id_seq")
    @SequenceGenerator(name = "header_id_seq", sequenceName = "header_id_seq")
    private Integer id;
    private int sequencial;

    @Column(name = "nome_arquivo")
    private String nomeArquivo;
    @Column(name = "data_geracao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataGeracao;

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

    public Date getDataGeracao() {
        return dataGeracao;
    }

    public void setDataGeracao(Date dataGeracao) {
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

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
