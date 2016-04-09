package br.com.clairtonluz.sicoba.service.comercial;

import br.com.clairtonluz.sicoba.model.entity.comercial.Conexao;
import br.com.clairtonluz.sicoba.model.entity.comercial.Contrato;
import br.com.clairtonluz.sicoba.repository.comercial.ContratoRepository;
import br.com.clairtonluz.sicoba.service.comercial.conexao.ConexaoService;
import br.com.clairtonluz.sicoba.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
public class ContratoService {

    @Autowired
    private ContratoRepository contratoRepository;
    @Autowired
    private ConexaoService conexaoService;

    public List<Contrato> buscarRecentes() {
        LocalDate hoje = LocalDate.now();
        Date to = DateUtil.toDate(hoje);
        Date from = DateUtil.toDate(hoje.minusDays(30));
        List<Contrato> result = contratoRepository.findByDataInstalacaoBetween(from, to);
        return result;
    }

    public void salvar(Contrato contrato) throws Exception {
        Conexao conexao = conexaoService.buscarOptionalPorCliente(contrato.getCliente());
        if (conexao != null) {
            conexaoService.save(conexao);
        }
        contratoRepository.save(contrato);
    }

    @Transactional
    public void remove(Integer id) {
        Contrato c = contratoRepository.findOne(id);
        contratoRepository.delete(c);
    }

    public Contrato buscarPorCliente(Integer clienteId) {
        return contratoRepository.findOptionalByCliente_id(clienteId);
    }

    @Transactional
    public Contrato save(Contrato contrato) {
        return contratoRepository.save(contrato);
    }
}
