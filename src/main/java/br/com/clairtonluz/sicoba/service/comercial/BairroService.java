package br.com.clairtonluz.sicoba.service.comercial;

import br.com.clairtonluz.sicoba.model.entity.comercial.Bairro;
import br.com.clairtonluz.sicoba.model.entity.comercial.Cidade;
import br.com.clairtonluz.sicoba.model.entity.comercial.Endereco;
import br.com.clairtonluz.sicoba.model.entity.comercial.Estado;
import br.com.clairtonluz.sicoba.repository.comercial.BairroRepository;
import br.com.clairtonluz.sicoba.repository.comercial.CidadeRepository;
import br.com.clairtonluz.sicoba.repository.comercial.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class BairroService {

    @Autowired
    private BairroRepository bairroRepository;
    @Autowired
    protected CidadeRepository cidadeRepository;
    @Autowired
    private EstadoRepository estadoRepository;

    @Transactional
    public Bairro buscarOuCriarBairro(Endereco endereco) {
        Bairro bairro = null;
        if (endereco != null) {
            bairro = bairroRepository.findOptionalByNome(endereco.getBairro().getNome());

            if (bairro == null) {
                Cidade cidade = cidadeRepository.findOptionalByNomeAndEstado_uf(endereco.getBairro().getCidade().getNome(),
                        endereco.getBairro().getCidade().getEstado().getUf());
                if (cidade != null) {
                    bairro = novoBairro(endereco, cidade);
                } else {
                    Estado estado = estadoRepository.findOptionalByUf(endereco.getBairro().getCidade().getEstado().getUf());
                    Cidade cidade1 = novaCidade(endereco, estado);
                    bairro = novoBairro(endereco, cidade1);
                }
            }
        }
        return bairro;
    }

    private Cidade novaCidade(Endereco endereco, Estado estado) {
        Cidade cidade = new Cidade();
        cidade.setEstado(estado);
        cidade.setNome(endereco.getBairro().getCidade().getNome());
        cidadeRepository.save(cidade);
        return cidade;
    }

    private Bairro novoBairro(Endereco endereco, Cidade cidade) {
        Bairro bairro;
        bairro = new Bairro();
        bairro.setNome(endereco.getBairro().getNome());
        bairro.setCidade(cidade);
        bairroRepository.save(bairro);
        return bairro;
    }

}
