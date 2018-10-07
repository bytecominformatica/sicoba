package br.com.clairtonluz.sicoba.model.entity.comercial;

import br.com.clairtonluz.sicoba.model.entity.estoque.Equipamento;
import br.com.clairtonluz.sicoba.model.entity.extra.BaseEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "contrato")
public class Contrato extends BaseEntity {

    @NotNull(message = "vencimento é obrigatório")
    private Integer vencimento;
    @Column(name = "data_instalacao")
    @NotNull(message = "data de instalação é obrigatório")
    private LocalDateTime dataInstalacao;


    @JoinColumn(name = "equipamento_wifi_id", referencedColumnName = "id")
    @OneToOne
    private Equipamento equipamentoWifi;

    @JoinColumn(name = "equipamento_id", referencedColumnName = "id")
    @OneToOne
    private Equipamento equipamento;

    @JoinColumn(name = "plano_id", referencedColumnName = "id")
    @ManyToOne
    @NotNull(message = "plano é obrigatório")
    private Plano plano;

    @JoinColumn(name = "cliente_id", referencedColumnName = "id")
    @OneToOne
    @NotNull(message = "cliente é obrigatório")
    private Cliente cliente;

    public Integer getVencimento() {
        return vencimento;
    }

    public void setVencimento(Integer vencimento) {
        this.vencimento = vencimento;
    }

    public LocalDateTime getDataInstalacao() {
        return dataInstalacao;
    }

    public void setDataInstalacao(LocalDateTime dataInstalacao) {
        this.dataInstalacao = dataInstalacao;
    }

    public Equipamento getEquipamentoWifi() {
        return equipamentoWifi;
    }

    public void setEquipamentoWifi(Equipamento equipamentoWifi) {
        this.equipamentoWifi = equipamentoWifi;
    }

    public Equipamento getEquipamento() {
        return equipamento;
    }

    public void setEquipamento(Equipamento equipamento) {
        this.equipamento = equipamento;
    }

    public Plano getPlano() {
        return plano;
    }

    public void setPlano(Plano plano) {
        this.plano = plano;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

}
