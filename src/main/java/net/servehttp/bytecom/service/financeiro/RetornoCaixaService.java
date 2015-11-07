package net.servehttp.bytecom.service.financeiro;

import net.servehttp.bytecom.commons.parse.ParseRetornoCaixa;
import net.servehttp.bytecom.model.jpa.comercial.ClienteJPA;
import net.servehttp.bytecom.model.jpa.entity.comercial.Cliente;
import net.servehttp.bytecom.model.jpa.entity.comercial.StatusCliente;
import net.servehttp.bytecom.model.jpa.entity.extra.EntityGeneric;
import net.servehttp.bytecom.model.jpa.entity.financeiro.Mensalidade;
import net.servehttp.bytecom.model.jpa.entity.financeiro.StatusMensalidade;
import net.servehttp.bytecom.model.jpa.entity.financeiro.retorno.Header;
import net.servehttp.bytecom.model.jpa.entity.financeiro.retorno.HeaderLote;
import net.servehttp.bytecom.model.jpa.entity.financeiro.retorno.Registro;
import net.servehttp.bytecom.model.jpa.financeiro.HeaderJPA;
import net.servehttp.bytecom.model.jpa.financeiro.MensalidadeJPA;
import net.servehttp.bytecom.pojo.financeiro.RetornoPojo;
import net.servehttp.bytecom.service.provedor.IConnectionControl;
import net.servehttp.bytecom.util.web.AlertaUtil;

import javax.inject.Inject;
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
    private MensalidadeJPA mensalidadeJPA;
    @Inject
    private HeaderJPA headerJPA;
    @Inject
    private ClienteJPA clienteJPA;
    @Inject
    private IConnectionControl connectionControl;

    public Header parse(InputStream inputStream, String filename) throws IOException {
        return PARSE_RETORNO.parse(inputStream, filename);
    }

    public List<RetornoPojo> processarHeader(Header header) throws Exception {
        List<RetornoPojo> retornoPojos = new ArrayList<>();
        if (notExists(header)) {
            List<EntityGeneric> mensalidadesRegistradas = new ArrayList<>();
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
            mensalidadeJPA.save(mensalidadesRegistradas);
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

    private Mensalidade liquidarMensalidade(Registro r) throws Exception {
        Mensalidade m = mensalidadeJPA.buscarPorModalidadeNumeroBoleto(r.getModalidadeNossoNumero(), r.getNossoNumero());

//                        TODO: remover isso quando todos os boletos estiverem registrados
        if (m == null) {
            m = mensalidadeJPA.buscarPorId(r.getNossoNumero());
        }

        if (m != null) {
            m.setStatus(StatusMensalidade.PAGO_NO_BOLETO);
            m.setValor(r.getValorTitulo());
            m.setValorPago(r.getRegistroDetalhe().getValorPago());
            m.setDesconto(r.getRegistroDetalhe().getDesconto());
            m.setTarifa(r.getValorTarifa());
            m.setDataOcorrencia(r.getRegistroDetalhe().getDataOcorrencia());
            mensalidadeJPA.save(m);

            if (m.getCliente().getStatus().equals(StatusCliente.INATIVO)) {
                m.getCliente().setStatus(StatusCliente.ATIVO);
                m.getCliente().getStatus().atualizarConexao(m.getCliente(), connectionControl);
                clienteJPA.save(m.getCliente());
            }

        }
        return m;
    }

    private Mensalidade criarMensalidadeRegistrada(Registro r) {
        String[] split = r.getNumeroDocumento().split("-");
        int clienteId = Integer.parseInt(split[0]);
        Cliente c = clienteJPA.buscarPorId(clienteId);
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
