package net.servehttp.bytecom.persistence.jpa.entity.comercial;

import net.servehttp.bytecom.persistence.jpa.entity.extra.EntityGeneric;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "pais")
public class Pais extends EntityGeneric {

    private static final long serialVersionUID = -8042147881454631916L;

    private String nome;

    @OneToMany(mappedBy = "pais", fetch = FetchType.EAGER)
    private List<Estado> estados;

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Estado> getEstados() {
        return this.estados;
    }

    public void setEstados(List<Estado> estados) {
        this.estados = estados;
    }

    public Estado addEstado(Estado estado) {
        getEstados().add(estado);
        estado.setPais(this);

        return estado;
    }

    public Estado removeEstado(Estado estado) {
        getEstados().remove(estado);
        estado.setPais(null);

        return estado;
    }

}
