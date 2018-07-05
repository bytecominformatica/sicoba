package br.com.clairtonluz.sicoba.model.entity.comercial;

import br.com.clairtonluz.sicoba.model.entity.extra.BaseEntity;
import br.com.clairtonluz.sicoba.model.entity.provedor.impl.Mikrotik;
import br.com.clairtonluz.sicoba.model.entity.provedor.impl.Secret;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name = "conexao")
public class Conexao extends BaseEntity {

    @OneToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "mikrotik_id")
    private Mikrotik mikrotik;

    private String nome;

    private String senha;

    @Pattern(message = "IP inválido",
            regexp = "^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$")
    @Size(min = 1, max = 40)
    private String ip;
    @Pattern(message = "MAC inválido",
            regexp = "^[a-fA-F0-9:]{17}|[a-fA-F0-9]{12}$")
    private String mac;

    public Secret createSecret(Plano plano) {
        boolean disabled = cliente.getStatus() != StatusCliente.ATIVO;
        return new Secret(nome, senha, ip, mac, plano.getNome(), disabled);
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return this.senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Mikrotik getMikrotik() {
        return mikrotik;
    }

    public void setMikrotik(Mikrotik mikrotik) {
        this.mikrotik = mikrotik;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }
}
