package br.com.clairtonluz.bytecom.model.service.comercial;

import br.com.clairtonluz.bytecom.model.jpa.entity.comercial.Cliente;
import br.com.clairtonluz.bytecom.model.jpa.entity.comercial.Conexao;
import br.com.clairtonluz.bytecom.model.jpa.entity.comercial.Contrato;
import br.com.clairtonluz.bytecom.model.jpa.entity.provedor.impl.Secret;
import br.com.clairtonluz.bytecom.model.repository.comercial.ConexaoRepository;
import br.com.clairtonluz.bytecom.model.repository.comercial.ContratoRepository;
import br.com.clairtonluz.bytecom.model.service.comercial.conexao.ConexaoOperacaoFactory;

import javax.inject.Inject;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public class ContratoService implements Serializable {

    private static final long serialVersionUID = 8705835474790847188L;
    @Inject
    private ContratoRepository contratoRepository;
    @Inject
    private ConexaoRepository conexaoRepository;
    @Inject
    private ConexaoOperacaoFactory conexaoOperacaoFactory;

    public List<Contrato> buscarRecentes() {
        LocalDate to = LocalDate.now();
        LocalDate from = to.minusDays(30);
        List<Contrato> result = contratoRepository.findByDataInstalacaoBetween(from, to);
        return result;
    }

    public void salvar(Contrato contrato) throws Exception {
        Conexao conexao = conexaoRepository.findOptionalByCliente(contrato.getCliente());
        if (conexao != null) {
            Secret secret = conexao.createSecret(contrato.getPlano());
            conexaoOperacaoFactory.create(conexao).executar(conexao, contrato.getPlano());
        }

        contratoRepository.save(contrato);
    }

    public void remover(Contrato contrato) {
        contratoRepository.remove(contrato);
    }

    public Contrato buscarPorCliente(Cliente cliente) {
        return contratoRepository.findByCliente(cliente);
    }
}
