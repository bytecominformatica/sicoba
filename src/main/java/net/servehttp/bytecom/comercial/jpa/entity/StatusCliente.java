package net.servehttp.bytecom.comercial.jpa.entity;

import java.util.Optional;

public enum StatusCliente {
  INATIVO {
    @Override
    public String getProfile(Cliente cliente) {
      return "INATIVO";
    }
  },
  ATIVO {
    @Override
    public String getProfile(Cliente cliente) {
      Optional<Cliente> c = Optional.ofNullable(cliente);
      return c.map(Cliente::getContrato).map(Contrato::getPlano).map(Plano::getNome).orElse(null);
    }
  },
  CANCELADO {
    @Override
    public String getProfile(Cliente cliente) {
      return null;
    }
  };

  public abstract String getProfile(Cliente cliente);

}
