package net.servehttp.bytecom.controller;

import java.util.Date;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 * @author Clairton Luz
 * 
 */
@Named
@RequestScoped
public class HtmlController {

  private String data;
  private Date data1;
  private String email;

  public void exibir() {
    System.out.println("email com sucesso = " + email);
    System.out.println("data com sucesso = " + data);
    System.out.println("data1 com sucesso = " + data1);
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getData() {
    return data;
  }

  public void setData(String data) {
    this.data = data;
  }

  public Date getData1() {
    return data1;
  }

  public void setData1(Date data1) {
    this.data1 = data1;
  }
}
