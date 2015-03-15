package net.servehttp.bytecom.comercial.jpa.entity;

import java.util.Optional;

import net.servehttp.bytecom.administrador.controller.ServidorController;
import net.servehttp.bytecom.provedor.service.mikrotik.MikrotikPPP;

public enum StatusCliente {
  INATIVO {
    @Override
    public String getProfile(Cliente cliente) {
      return "INATIVO";
    }

    @Override
    public void atualizarConexao(Cliente cliente, MikrotikPPP mikrotikPPP) throws Exception {
      if (cliente.getConexao() != null) {
        mikrotikPPP.salvarSecret(cliente.getConexao());
      }
    }
  },
  ATIVO {
    @Override
    public String getProfile(Cliente cliente) {
      Optional<Cliente> c = Optional.ofNullable(cliente);
      return c.map(Cliente::getContrato).map(Contrato::getPlano).map(Plano::getNome).orElse(null);
    }

    @Override
    public void atualizarConexao(Cliente cliente, MikrotikPPP mikrotikPPP) throws Exception {
      if (cliente.getConexao() != null) {
        mikrotikPPP.salvarSecret(cliente.getConexao());
      }
    }
  },
  CANCELADO {
    @Override
    public String getProfile(Cliente cliente) {
      return null;
    }

    @Override
    public void atualizarConexao(Cliente cliente, MikrotikPPP mikrotikPPP) throws Exception {
      if (cliente.getConexao() != null) {
        mikrotikPPP.removerSecret(cliente.getConexao());
        cliente.setConexao(null);
      }
    }
  };

  private static final ServidorController SERVIDOR_CONTROLLER = new ServidorController();

  public abstract String getProfile(Cliente cliente);

  public abstract void atualizarConexao(Cliente cliente, MikrotikPPP mikrotikPPP) throws Exception;

  public void atualizarAcesso() {
    SERVIDOR_CONTROLLER.atualizarAcesso();
  }

}
