package net.servehttp.bytecom.model.jpa.entity.comercial;

import net.servehttp.bytecom.service.provedor.IConnectionControl;
import net.servehttp.bytecom.service.provedor.IFirewall;
import net.servehttp.bytecom.service.provedor.impl.MikrotikFirewall;

public enum StatusCliente {

    INATIVO {
        @Override
        public void atualizarConexao(Cliente cliente, IConnectionControl control) throws Exception {
            if (cliente.getConexao() != null) {
                control.save(cliente.getConexao().getMikrotik(), cliente.getConexao());
                FIREWALL.lock(cliente.getConexao().getMikrotik(), cliente.getConexao());
            }
        }
    },
    ATIVO {
        @Override
        public void atualizarConexao(Cliente cliente, IConnectionControl control) throws Exception {
            if (cliente.getConexao() != null) {
                control.save(cliente.getConexao().getMikrotik(), cliente.getConexao());
                FIREWALL.unlock(cliente.getConexao().getMikrotik(), cliente.getConexao());
            }
        }
    },
    CANCELADO {
        @Override
        public void atualizarConexao(Cliente cliente, IConnectionControl control) throws Exception {
            if (cliente.getConexao() != null) {
                control.remove(cliente.getConexao().getMikrotik(), cliente.getConexao());
                cliente.setConexao(null);
            }
        }
    };


    private static final IFirewall FIREWALL = new MikrotikFirewall();

    public abstract void atualizarConexao(Cliente cliente, IConnectionControl server) throws Exception;

}
