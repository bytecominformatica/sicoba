package br.com.clairtonluz.bytecom.model.service.comercial;

import br.com.clairtonluz.bytecom.model.jpa.comercial.ConexaoJPA;
import br.com.clairtonluz.bytecom.model.jpa.entity.comercial.Conexao;
import br.com.clairtonluz.bytecom.model.service.CrudService;

import javax.inject.Inject;
import java.util.List;

public class ConnectionService extends CrudService {

    private static final long serialVersionUID = -8296012997453708684L;

    @Inject
    private ConexaoJPA conexaoJPA;

    public List<Conexao> buscarTodos() {
        return conexaoJPA.buscarTodos();
    }
}
