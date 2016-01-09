package br.com.clairtonluz.bytecom.model.service.financeiro;

import br.com.clairtonluz.bytecom.commons.parse.ParseRetornoCaixa;
import br.com.clairtonluz.bytecom.model.jpa.entity.comercial.Cliente;
import br.com.clairtonluz.bytecom.model.jpa.entity.comercial.Conexao;
import br.com.clairtonluz.bytecom.model.jpa.entity.comercial.Contrato;
import br.com.clairtonluz.bytecom.model.jpa.entity.comercial.StatusCliente;
import br.com.clairtonluz.bytecom.model.jpa.entity.financeiro.Mensalidade;
import br.com.clairtonluz.bytecom.model.jpa.entity.financeiro.StatusMensalidade;
import br.com.clairtonluz.bytecom.model.jpa.entity.financeiro.retorno.Header;
import br.com.clairtonluz.bytecom.model.jpa.entity.financeiro.retorno.HeaderLote;
import br.com.clairtonluz.bytecom.model.jpa.entity.financeiro.retorno.Registro;
import br.com.clairtonluz.bytecom.model.jpa.financeiro.HeaderJPA;
import br.com.clairtonluz.bytecom.model.repository.comercial.ClienteRepository;
import br.com.clairtonluz.bytecom.model.repository.comercial.ContratoRepository;
import br.com.clairtonluz.bytecom.model.repository.financeiro.MensalidadeRepository;
import br.com.clairtonluz.bytecom.model.service.comercial.conexao.ConexaoService;
import br.com.clairtonluz.bytecom.model.service.provedor.IConnectionControl;
import br.com.clairtonluz.bytecom.pojo.financeiro.RetornoPojo;
import br.com.clairtonluz.bytecom.util.web.AlertaUtil;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by clairtonluz on 31/10/15.
 */
public class RetornoCaixaService implements Serializable {
    private static ParseRetornoCaixa PARSE_RETORNO = new ParseRetornoCaixa();

    @Inject
    private MensalidadeRepository mensalidadeRepository;
    @Inject
    private HeaderJPA headerJPA;
    @Inject
    private ClienteRepository clienteRepository;
    @Inject
    private ContratoRepository contratoRepository;
    @Inject
    private ConexaoService conexaoService;
    @Inject
    private IConnectionControl connectionControl;

    public Header parse(InputStream inputStream, String filename) throws IOException {
        return PARSE_RETORNO.parse(inputStream, filename);
    }

    @Transactional
    public List<RetornoPojo> processarHeader(Header header) throws Exception {
        List<RetornoPojo> retornoPojos = new ArrayList<>();
        if (notExists(header)) {
            List<Mensalidade> mensalidadesRegistradas = new ArrayList<>();
            for (HeaderLote hl : header.getHeaderLotes()) {
                for (Registro r : hl.getRegistros()) {
                    if (r.getCodigoMovimento() == Registro.ENTRADA_CONFIRMADA) {
                        mensalidadesRegistradas.add(criarMensalidadeRegistrada(r));
                    } else if (r.getCodigoMovimento() == Registro.LIQUIDACAO) {
                        Mensalidade m = liquidarMensalidade(r);
                        RetornoPojo pojo = new RetornoPojo();

                        pojo.setMensalidade(m);
                        pojo.setMovimento("LIQUIDAÇÂO");
                        retornoPojos.add(pojo);
                    }
                }
            }
            mensalidadesRegistradas.forEach((it) -> {
                mensalidadeRepository.save(it);
            });
            mensalidadesRegistradas.forEach(mensalidade -> {
                Mensalidade m = (Mensalidade) mensalidade;
                RetornoPojo r = new RetornoPojo();

                r.setMensalidade(m);
                r.setMovimento("ENTRADA CONFIRMADA");
                retornoPojos.add(r);
            });
            headerJPA.save(header);
        }
        return retornoPojos;
    }

    @Transactional
    private Mensalidade liquidarMensalidade(Registro r) throws Exception {
        Mensalidade m = mensalidadeRepository.findOptionalByNumeroBoletoAndModalidade(r.getNossoNumero(), r.getModalidadeNossoNumero());

        if (m != null) {
            m.setStatus(StatusMensalidade.PAGO_NO_BOLETO);
            m.setValor(r.getValorTitulo());
            m.setValorPago(r.getRegistroDetalhe().getValorPago());
            m.setDesconto(r.getRegistroDetalhe().getDesconto());
            m.setTarifa(r.getValorTarifa());
            m.setDataOcorrencia(r.getRegistroDetalhe().getDataOcorrencia());
            mensalidadeRepository.save(m);

            if (m.getCliente().getStatus().equals(StatusCliente.INATIVO)) {
                m.getCliente().setStatus(StatusCliente.ATIVO);

                Cliente cliente = m.getCliente();
                Contrato contrato = contratoRepository.findOptionalByCliente_id(cliente.getId());
                Conexao conexao = conexaoService.buscarOptionalPorCliente(cliente);
                clienteRepository.save(m.getCliente());
                conexaoService.save(conexao);
            }
        }
        return m;
    }

    private Mensalidade criarMensalidadeRegistrada(Registro r) {
        String[] split = r.getNumeroDocumento().split("-");
        int clienteId = Integer.parseInt(split[0]);
        Cliente c = clienteRepository.findBy(clienteId);
        Mensalidade m = new Mensalidade();
        m.setCliente(c);
        m.setDataVencimento(r.getVencimento());
        m.setDesconto(r.getRegistroDetalhe().getDesconto());
        m.setModalidade(r.getModalidadeNossoNumero());
        m.setNumeroBoleto(r.getNossoNumero());
        m.setValor(r.getValorTitulo());
        return m;
    }

    private boolean notExists(Header header) {
        boolean exists = false;
        List<Header> list = headerJPA.buscarTodosPorSequencial(header.getSequencial());
        if (!list.isEmpty()) {
            exists = true;
            AlertaUtil.error("Arquivo já foi enviado");
        }
        return !exists;
    }
}
