package net.servehttp.bytecom.business;

import javax.inject.Inject;

import net.servehttp.bytecom.persistence.GenericoJPA;

public abstract class genericoBusiness {

  @Inject
  protected GenericoJPA genericoJPA;

  public <T> T salvar(T t) {
    return genericoJPA.salvar(t);
  }

  public <T> T atualizar(T t) {    
    return genericoJPA.atualizar(t);
  }
  
  public <T> void remover(T t) {
    genericoJPA.remover(t);
  }
  
}
