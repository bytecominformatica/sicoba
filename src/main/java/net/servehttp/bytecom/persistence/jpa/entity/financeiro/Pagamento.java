package net.servehttp.bytecom.persistence.jpa.entity.financeiro;


import net.servehttp.bytecom.persistence.jpa.entity.extra.EntityGeneric;
import net.servehttp.bytecom.persistence.jpa.entity.financeiro.retorno.Registro;
import net.servehttp.bytecom.util.converter.date.LocalDatePersistenceConverter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * Created by <a href="https://github.com/clairtonluz">Clairton Luz</a>
 */
@Entity
@Table(name = "pagamento")
public class Pagamento extends EntityGeneric {

    private static final long serialVersionUID = -8955481650524371350L;

    @Convert(converter = LocalDatePersistenceConverter.class)
    private LocalDate data;
    private Double valor;
    private Double desconto;

    @OneToOne
    @JoinColumn(name = "registro_id")
    private Registro registro;

    @ManyToOne
    @JoinColumn(name = "mensalidade_id")
    private Mensalidade mensalidade;

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Double getDesconto() {
        return desconto;
    }

    public void setDesconto(Double desconto) {
        this.desconto = desconto;
    }

    public Registro getRegistro() {
        return registro;
    }

    public void setRegistro(Registro registro) {
        this.registro = registro;
    }
}
