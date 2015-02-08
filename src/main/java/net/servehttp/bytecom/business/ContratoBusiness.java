package net.servehttp.bytecom.business;

import java.io.Serializable;

import javax.inject.Inject;

import net.servehttp.bytecom.persistence.ContratoJPA;
import net.servehttp.bytecom.persistence.entity.cadastro.Contrato;

public class ContratoBusiness implements Serializable {

  private static final long serialVersionUID = 8705835474790847188L;
  @Inject
  private ContratoJPA contratoJPA;

  public void remover(Contrato c) {
    contratoJPA.remover(c);
  }

  public Contrato buscarPorId(int id) {
    return contratoJPA.buscarPorId(Contrato.class, id);
  }

  public <T> T salvar(T t) {
    return salvar(t);
  }

  public <T> T atualizar(T t) {
    return atualizar(t);
  }

}
