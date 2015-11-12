package br.com.clairtonluz.bytecom.model.jpa.entity.comercial;

import br.com.clairtonluz.bytecom.service.provedor.IConnectionControl;
import br.com.clairtonluz.bytecom.service.provedor.IFirewall;
import br.com.clairtonluz.bytecom.service.provedor.impl.MikrotikFirewall;

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
//                control.remove(cliente.getConexao().getMikrotik(), cliente.getConexao());
                cliente.setConexao(null);
            }
            if (cliente.getContrato() != null) {
                cliente.getContrato().setEquipamento(null);
                cliente.getContrato().setEquipamentoWifi(null);
            }
        }
    };


    private static final IFirewall FIREWALL = new MikrotikFirewall();

    public abstract void atualizarConexao(Cliente cliente, IConnectionControl server) throws Exception;

}
