package br.com.clairtonluz.bytecom.service.comercial;

import br.com.clairtonluz.bytecom.model.jpa.comercial.ClienteJPA;
import br.com.clairtonluz.bytecom.model.jpa.comercial.ConexaoJPA;
import br.com.clairtonluz.bytecom.model.jpa.entity.comercial.Cliente;
import br.com.clairtonluz.bytecom.model.jpa.entity.comercial.Conexao;
import br.com.clairtonluz.bytecom.model.jpa.entity.comercial.StatusCliente;
import br.com.clairtonluz.bytecom.service.CrudService;
import br.com.clairtonluz.bytecom.service.provedor.IConnectionControl;
import br.com.clairtonluz.bytecom.service.provedor.MikrotikConnection;
import br.com.clairtonluz.bytecom.util.MensagemException;
import com.mysema.query.jpa.impl.JPAQuery;

import javax.inject.Inject;
import java.io.Serializable;
import java.util.List;

public class ConnectionService extends CrudService {

    private static final long serialVersionUID = -8296012997453708684L;

    @Inject
    private ConexaoJPA conexaoJPA;

    public List<Conexao> buscarTodos() {
        return conexaoJPA.buscarTodos();
    }
}
