package br.com.clairtonluz.bytecom.model.service.comercial;

import br.com.clairtonluz.bytecom.model.jpa.entity.comercial.Cidade;
import br.com.clairtonluz.bytecom.model.repository.comercial.CidadeRepository;

import javax.inject.Inject;
import java.io.Serializable;
import java.util.List;

public class CidadeService implements Serializable {

    private static final long serialVersionUID = -8296012997453708684L;

    @Inject
    private CidadeRepository cidadeRepository;

    public List<Cidade> buscarTodos() {
        return cidadeRepository.findAllOrderByNome();
    }

}
