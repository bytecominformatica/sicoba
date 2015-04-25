package net.servehttp.bytecom.persistence.jpa.entity.comercial;

import java.util.Optional;

import net.servehttp.bytecom.service.provedor.IConnectionControl;

public enum StatusCliente {
  INATIVO {
    @Override
    public String getProfile(Cliente cliente) {
      return "INATIVO";
    }

    @Override
    public void atualizarConexao(Cliente cliente, IConnectionControl control) throws Exception {
      if (cliente.getConexao() != null) {
        control.save(cliente.getConexao().getMikrotik(), cliente.getConexao());
        control.lock(cliente.getConexao().getMikrotik(), cliente.getConexao());
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
    public void atualizarConexao(Cliente cliente, IConnectionControl control) throws Exception {
      if (cliente.getConexao() != null) {
        control.save(cliente.getConexao().getMikrotik(), cliente.getConexao());
        control.unlock(cliente.getConexao().getMikrotik(), cliente.getConexao());
      }
    }
  },
  CANCELADO {
    @Override
    public String getProfile(Cliente cliente) {
      return null;
    }

    @Override
    public void atualizarConexao(Cliente cliente, IConnectionControl control) throws Exception {
      if (cliente.getConexao() != null) {
        control.unlock(cliente.getConexao().getMikrotik(), cliente.getConexao());
        control.remove(cliente.getConexao().getMikrotik(), cliente.getConexao());
        cliente.setConexao(null);
      }
    }
  };


  public abstract String getProfile(Cliente cliente);

  public abstract void atualizarConexao(Cliente cliente, IConnectionControl server) throws Exception;

}
