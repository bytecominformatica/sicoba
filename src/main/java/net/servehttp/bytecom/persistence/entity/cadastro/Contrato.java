package net.servehttp.bytecom.persistence.entity.cadastro;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import net.servehttp.bytecom.util.DateUtil;

@Entity
@Table(name = "contrato")
public class Contrato implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  @NotNull(message = "vencimento é obrigatório")
  private short vencimento;
  @Column(name = "data_instalacao")
  @NotNull(message = "data de instalação é obrigatório")
  @Temporal(TemporalType.DATE)
  private Date dataInstalacao;


  @JoinColumn(name = "equipamento_wifi_id", referencedColumnName = "id")
  @OneToOne(fetch = FetchType.EAGER)
  private Equipamento equipamentoWifi;

  @JoinColumn(name = "equipamento_id", referencedColumnName = "id")
  @OneToOne(fetch = FetchType.EAGER)
  private Equipamento equipamento;

  @JoinColumn(name = "plano_id", referencedColumnName = "id")
  @ManyToOne(fetch = FetchType.EAGER)
  @NotNull(message = "plano é obrigatório")
  private Plano plano;

  @JoinColumn(name = "cliente_id", referencedColumnName = "id")
  @OneToOne(fetch = FetchType.EAGER)
  @NotNull(message = "cliente é obrigatório")
  private Cliente cliente;

  @Column(name = "created_at")
  @Temporal(TemporalType.TIMESTAMP)
  private Date createdAt;

  @Column(name = "updated_at")
  @Temporal(TemporalType.TIMESTAMP)
  private Date updatedAt;


  public short getVencimento() {
    return vencimento;
  }

  public void setVencimento(short vencimento) {
    this.vencimento = vencimento;
  }

  public Date getDataInstalacao() {
    return dataInstalacao;
  }

  public String getDataInstalacaoFormatada() {
    return DateUtil.INSTANCE.format(dataInstalacao);
  }

  public void setDataInstalacaoFormatada(String dataInstalacao) {
    this.dataInstalacao = DateUtil.INSTANCE.parse(dataInstalacao, "MM/dd/yyyy");
  }

  public void setDataInstalacao(Date dataInstalacao) {
    this.dataInstalacao = dataInstalacao;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  public Date getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Date updatedAt) {
    this.updatedAt = updatedAt;
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

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

}