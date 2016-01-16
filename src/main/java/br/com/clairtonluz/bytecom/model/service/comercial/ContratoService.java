package br.com.clairtonluz.bytecom.model.service.comercial;

import br.com.clairtonluz.bytecom.model.entity.comercial.Conexao;
import br.com.clairtonluz.bytecom.model.entity.comercial.Contrato;
import br.com.clairtonluz.bytecom.model.entity.provedor.impl.Secret;
import br.com.clairtonluz.bytecom.model.repository.comercial.ConexaoRepository;
import br.com.clairtonluz.bytecom.model.repository.comercial.ContratoRepository;
import br.com.clairtonluz.bytecom.model.service.comercial.conexao.ConexaoOperacaoFactory;
import br.com.clairtonluz.bytecom.util.DateUtil;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
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
        LocalDate hoje = LocalDate.now();
        Date to = DateUtil.toDate(hoje);
        Date from = DateUtil.toDate(hoje.minusDays(30));
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

    @Transactional
    public void remove(Integer id) {
        Contrato c = contratoRepository.findBy(id);
        contratoRepository.remove(c);
    }

    public Contrato buscarPorCliente(Integer clienteId) {
        return contratoRepository.findOptionalByCliente_id(clienteId);
    }

    @Transactional
    public Contrato save(Contrato contrato) {
        return contratoRepository.save(contrato);
    }
}
