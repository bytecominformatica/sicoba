package br.com.clairtonluz.sicoba.service.comercial;

import br.com.clairtonluz.sicoba.model.entity.comercial.Conexao;
import br.com.clairtonluz.sicoba.model.entity.comercial.Contrato;
import br.com.clairtonluz.sicoba.repository.comercial.ContratoRepository;
import br.com.clairtonluz.sicoba.service.comercial.conexao.ConexaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
public class ContratoService {

    @Autowired
    private ContratoRepository contratoRepository;
    @Autowired
    private ConexaoService conexaoService;

    public List<Contrato> buscarRecentes() {
        LocalDate to = LocalDate.now();
        LocalDate from = to.minusDays(30);
        return contratoRepository.findByDataInstalacaoBetween(from, to);
    }

    @Transactional
    public Contrato save(Contrato contrato) {
        Contrato save = contratoRepository.save(contrato);
        Conexao conexao = conexaoService.buscarOptionalPorCliente(contrato.getCliente());
        if (conexao != null) {
            conexaoService.save(conexao);
        }
        return save;
    }

    @Transactional
    public void remove(Integer id) {
        Contrato c = contratoRepository.getOne(id);
        contratoRepository.delete(c);
    }

    public Contrato buscarPorCliente(Integer clienteId) {
        return contratoRepository.findOptionalByCliente_id(clienteId);
    }

    public Contrato buscarPorEquipamento(Integer equipamentoId) {
        return contratoRepository.findByEquipamento(equipamentoId);
    }
}
