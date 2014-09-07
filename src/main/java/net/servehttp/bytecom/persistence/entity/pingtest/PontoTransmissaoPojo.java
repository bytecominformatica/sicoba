package net.servehttp.bytecom.persistence.entity.pingtest;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PontoTransmissaoPojo implements Serializable {

  private static final long serialVersionUID = 5338742150590138903L;

  private int ip1;
  private int ip2;
  private int ip3;
  private int ip4;
  private String mac;
  private List<PontoTransmissaoPojo> transmitePara = new ArrayList<>();
  private String observacoes;
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

  public List<PontoTransmissaoPojo> getTransmitePara() {
    return transmitePara;
  }

  public void setTransmitePara(List<PontoTransmissaoPojo> transmitePara) {
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
