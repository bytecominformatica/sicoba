package net.servehttp.bytecom.business;

import java.io.Serializable;

import javax.inject.Inject;

import net.servehttp.bytecom.persistence.ContratoJPA;
import net.servehttp.bytecom.persistence.entity.cadastro.Contrato;

public class ContratoBusiness extends genericoBusiness implements Serializable {

  private static final long serialVersionUID = 8705835474790847188L;
  @Inject
  private ContratoJPA contratoJPA;

  public void remover(Contrato c) {
    contratoJPA.remover(c);
  }

  public Contrato findById(int id) {
    return genericoJPA.findById(Contrato.class, id);
  }

}
