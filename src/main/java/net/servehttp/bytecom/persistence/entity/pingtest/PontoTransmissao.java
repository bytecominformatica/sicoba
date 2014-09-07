package net.servehttp.bytecom.persistence.entity.pingtest;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

import net.servehttp.bytecom.persistence.entity.cadastro.EntityGeneric;

import com.sun.istack.NotNull;

 @Entity
 @Table(name="ponto_transmissao")
public class PontoTransmissao extends EntityGeneric implements Serializable {

  private static final long serialVersionUID = 5338742150590138903L;

  @Max(value = 255, message = "IP inválido")
  @Min(value = 0, message = "IP inválido")
  @NotNull
  private int ip1;
  @Max(value = 255, message = "IP inválido")
  @Min(value = 0, message = "IP inválido")
  @NotNull
  private int ip2;
  @Max(value = 255, message = "IP inválido")
  @Min(value = 0, message = "IP inválido")
  @NotNull
  private int ip3;
  @Max(value = 255, message = "IP inválido")
  @Min(value = 0, message = "IP inválido")
  @NotNull
  private int ip4;
  @Pattern(
      regexp = "^[0-9A-Fa-f]{2}:[0-9A-Fa-f]{2}:[0-9A-Fa-f]{2}:[0-9A-Fa-f]{2}:[0-9A-Fa-f]{2}:[0-9A-Fa-f]{2}$",
      message = "MAC inválido")
  private String mac;
  
  @ManyToOne(fetch=FetchType.EAGER)
  @JoinColumn(name="recebe_de")
  private PontoTransmissao recebeDe;
  @OneToMany(mappedBy="recebeDe", fetch=FetchType.EAGER)
  private List<PontoTransmissao> transmitePara;
  
  @Lob
  private String observacoes;
  
  @Transient
  private boolean online;
  
  public int getIp1() {
    return ip1;
  }


  public void setIp1(int ip1) {
    this.ip1 = ip1;
  }


  public int getIp2() {
    return ip2;
  }


  public void setIp2(int ip2) {
    this.ip2 = ip2;
  }


  public int getIp3() {
    return ip3;
  }


  public void setIp3(int ip3) {
    this.ip3 = ip3;
  }


  public int getIp4() {
    return ip4;
  }


  public void setIp4(int ip4) {
    this.ip4 = ip4;
  }


  public PontoTransmissao getRecebeDe() {
    return recebeDe;
  }


  public void setRecebeDe(PontoTransmissao recebeDe) {
    this.recebeDe = recebeDe;
  }


  public List<PontoTransmissao> getTransmitePara() {
    return transmitePara;
  }


  public void setTransmitePara(List<PontoTransmissao> transmitePara) {
    this.transmitePara = transmitePara;
  }


  public String getMac() {
    return mac;
  }


  public void setMac(String mac) {
    this.mac = mac;
  }


  public boolean isOnline() {
    return online;
  }


  public void setOnline(boolean online) {
    this.online = online;
  }


  public String getObservacoes() {
    return observacoes;
  }


  public void setObservacoes(String observacoes) {
    this.observacoes = observacoes;
  }

}
