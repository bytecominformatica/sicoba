package net.servehttp.bytecom.persistence.jpa.entity.comercial;

import net.servehttp.bytecom.service.provedor.IConnectionControl;

public enum StatusCliente {
  INATIVO {
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
    public void atualizarConexao(Cliente cliente, IConnectionControl control) throws Exception {
      if (cliente.getConexao() != null) {
        control.save(cliente.getConexao().getMikrotik(), cliente.getConexao());
        control.unlock(cliente.getConexao().getMikrotik(), cliente.getConexao());
      }
    }
  },
  CANCELADO {
    @Override
    public void atualizarConexao(Cliente cliente, IConnectionControl control) throws Exception {
      if (cliente.getConexao() != null) {
        control.unlock(cliente.getConexao().getMikrotik(), cliente.getConexao());
        control.remove(cliente.getConexao().getMikrotik(), cliente.getConexao());
        cliente.setConexao(null);
      }
    }
  };

  public abstract void atualizarConexao(Cliente cliente, IConnectionControl server) throws Exception;

}
