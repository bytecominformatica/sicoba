package net.servehttp.bytecom.rest.extra;

import net.servehttp.bytecom.persistence.jpa.entity.comercial.Endereco;
import net.servehttp.bytecom.persistence.jpa.entity.comercial.StatusCliente;

public class ClientGeoRefPOJO {

  private String nome;
  private String latitude;
  private String longitude;
  private Enum<StatusCliente> status;
  private Endereco endereco;
  private String numero;
  private String foneTitular;
  private String foneContato;
  private String contato;
  
  public String getNome() {
    return nome;
  }
  public void setNome(String nome) {
    this.nome = nome;
  }
  public String getLatitude() {
    return latitude;
  }
  public void setLatitude(String latitude) {
    this.latitude = latitude;
  }
  public String getLongitude() {
    return longitude;
  }
  public void setLongitude(String longitude) {
    this.longitude = longitude;
  }
  public Enum<StatusCliente> getStatus() {
    return status;
  }
  public Endereco getEndereco() {
    return endereco;
  }
  public void setEndereco(Endereco endereco) {
    this.endereco = endereco;
  }
  public String getNumero() {
    return numero;
  }
  public void setNumero(String numero) {
    this.numero = numero;
  }
  public void setStatus(Enum<StatusCliente> status) {
    this.status = status;
  }
  public String getFoneTitular() {
    return foneTitular;
  }
  public void setFoneTitular(String foneTitular) {
    this.foneTitular = foneTitular;
  }
  public String getFoneContato() {
    return foneContato;
  }
  public void setFoneContato(String foneContato) {
    this.foneContato = foneContato;
  }
  public String getContato() {
    return contato;
  }
  public void setContato(String contato) {
    this.contato = contato;
  }
  
  
  
  
}
