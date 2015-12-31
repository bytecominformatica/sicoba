package br.com.clairtonluz.bytecom.model.service.comercial;

import br.com.clairtonluz.bytecom.model.jpa.entity.comercial.Bairro;
import br.com.clairtonluz.bytecom.model.jpa.entity.comercial.Cliente;
import br.com.clairtonluz.bytecom.model.repository.comercial.ClienteRepository;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public class BairroService implements Serializable {

    private static final long serialVersionUID = -8296012997453708684L;

    @Inject
    private ClienteRepository clienteRepository;


//    public List<Bairro> buscarTodos() {
//        LocalDateTime data = LocalDateTime.now().minusMonths(2);
//        return clienteRepository.findByUpdatedAtGreaterThan(data);
//    }

    public Cliente buscarPorId(Integer id) {
        return clienteRepository.findBy(id);
    }

    @Transactional
    public Cliente save(Cliente cliente) throws Exception {


        return cliente;
    }

}
