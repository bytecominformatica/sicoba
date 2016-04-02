package br.com.clairtonluz.sicoba.service.financeiro;

import br.com.clairtonluz.sicoba.exception.ConflitException;
import br.com.clairtonluz.sicoba.model.entity.comercial.Cliente;
import br.com.clairtonluz.sicoba.model.entity.comercial.Conexao;
import br.com.clairtonluz.sicoba.model.entity.comercial.Contrato;
import br.com.clairtonluz.sicoba.model.entity.comercial.StatusCliente;
import br.com.clairtonluz.sicoba.model.entity.financeiro.StatusTitulo;
import br.com.clairtonluz.sicoba.model.entity.financeiro.Titulo;
import br.com.clairtonluz.sicoba.model.entity.financeiro.retorno.Header;
import br.com.clairtonluz.sicoba.model.entity.financeiro.retorno.HeaderLote;
import br.com.clairtonluz.sicoba.model.entity.financeiro.retorno.Registro;
import br.com.clairtonluz.sicoba.model.pojo.financeiro.RetornoPojo;
import br.com.clairtonluz.sicoba.parse.ParseRetornoCaixa;
import br.com.clairtonluz.sicoba.repository.comercial.ClienteRepository;
import br.com.clairtonluz.sicoba.repository.comercial.ConexaoRepository;
import br.com.clairtonluz.sicoba.repository.comercial.ContratoRepository;
import br.com.clairtonluz.sicoba.repository.financeiro.HeaderRepository;
import br.com.clairtonluz.sicoba.service.provedor.IConnectionControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by clairtonluz on 31/10/15.
 */
@Service
public class RetornoCaixaService {
    private static ParseRetornoCaixa PARSE_RETORNO = new ParseRetornoCaixa();

    @Autowired
    private TituloService tituloService;
    @Autowired
    private HeaderRepository headerRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private ContratoRepository contratoRepository;
    @Autowired
    private ConexaoRepository conexaoRepository;
    @Autowired
    private IConnectionControl connectionControl;

    public Header parse(InputStream inputStream, String filename) throws IOException {
        return PARSE_RETORNO.parse(inputStream, filename);
    }

    @Transactional
    public List<RetornoPojo> processarHeader(Header header) throws Exception {
        List<RetornoPojo> retornoPojos = new ArrayList<>();
        if (notExists(header)) {
            List<Titulo> titulosRegistrados = new ArrayList<>();
            for (HeaderLote hl : header.getHeaderLotes()) {
                for (Registro r : hl.getRegistros()) {
                    if (r.getCodigoMovimento() == Registro.ENTRADA_CONFIRMADA) {
                        titulosRegistrados.add(criarTituloRegistrada(r));
                    } else if (r.getCodigoMovimento() == Registro.LIQUIDACAO) {
                        Titulo m = liquidarTitulo(r);
                        RetornoPojo pojo = new RetornoPojo();

                        pojo.setTitulo(m);
                        pojo.setMovimento("LIQUIDAÇÂO");
                        pojo.setNossoNumero(r.getNossoNumero());
                        retornoPojos.add(pojo);
                    }
                }
            }
            tituloService.save(titulosRegistrados);

            titulosRegistrados.forEach(titulo -> {
                Titulo m = titulo;
                RetornoPojo r = new RetornoPojo();

                r.setTitulo(m);
                r.setMovimento("ENTRADA CONFIRMADA");
                retornoPojos.add(r);
            });

            headerRepository.save(header);
        } else {
            throw new ConflitException("Arquivo já foi enviado");
        }

        return retornoPojos;
    }

    @Transactional
    private Titulo liquidarTitulo(Registro r) throws Exception {
        Titulo m = tituloService.buscarPorBoleto(r.getNossoNumero());

        if (m != null) {
            m.setStatus(StatusTitulo.PAGO_NO_BOLETO);
            m.setValor(r.getValorTitulo());
            m.setValorPago(r.getRegistroDetalhe().getValorPago());
            m.setDesconto(r.getRegistroDetalhe().getDesconto());
            m.setTarifa(r.getValorTarifa());
            m.setDataOcorrencia(r.getRegistroDetalhe().getDataOcorrencia());
            tituloService.save(m);

            if (m.getCliente().getStatus().equals(StatusCliente.INATIVO)) {
                m.getCliente().setStatus(StatusCliente.ATIVO);

                Cliente cliente = m.getCliente();
                Contrato contrato = contratoRepository.findOptionalByCliente_id(cliente.getId());
                Conexao conexao = conexaoRepository.findOptionalByCliente(cliente);
                clienteRepository.save(m.getCliente());
                conexaoRepository.save(conexao);
            }
        }
        return m;
    }

    private Titulo criarTituloRegistrada(Registro r) {
        String[] split = r.getNumeroDocumento().split("-");
        int clienteId = Integer.parseInt(split[0]);
        Cliente c = clienteRepository.findOne(clienteId);
        Titulo m = new Titulo();
        m.setCliente(c);
        m.setDataVencimento(r.getVencimento());
        m.setDesconto(r.getRegistroDetalhe().getDesconto());
        m.setModalidade(r.getModalidadeNossoNumero());
        m.setNumeroBoleto(r.getNossoNumero());
        m.setValor(r.getValorTitulo());
        return m;
    }

    private boolean notExists(Header header) {
        List<Header> list = headerRepository.findBySequencial(header.getSequencial());
        return list.isEmpty();
    }
}
