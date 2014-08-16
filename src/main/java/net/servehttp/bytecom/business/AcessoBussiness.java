package net.servehttp.bytecom.business;

import java.io.Serializable;

import javax.inject.Inject;

import net.servehttp.bytecom.persistence.AcessoJPA;
import net.servehttp.bytecom.persistence.entity.cadastro.Acesso;

public class AcessoBussiness extends genericoBusiness implements Serializable {

  private static final long serialVersionUID = 8705835474790847188L;
  @Inject
  private AcessoJPA acessoJPA;

  public void remover(Acesso a) {
    acessoJPA.remover(a);
  }

  public Acesso findById(int id) {
    return genericoJPA.findById(Acesso.class, id);
  }

  public boolean isIpDisponivel(Acesso a) {
    Acesso a1 = genericoJPA.buscarUm("ip", a.getIp(), Acesso.class);
    return a1 == null || a1.getId() == a.getId();
  }

  public boolean isMacDisponivel(Acesso a) {
    Acesso a1 = genericoJPA.buscarUm("mac", a.getMac(), Acesso.class);
    return a1 == null || a1.getId() == a.getId();
  }

  public String getIpLivre() {
    return acessoJPA.getIpLivre();
  }


}
