package net.servehttp.bytecom.service.financeiro;

import net.servehttp.bytecom.persistence.jpa.entity.comercial.Cliente;
import net.servehttp.bytecom.persistence.jpa.entity.financeiro.Cedente;
import net.servehttp.bytecom.persistence.jpa.entity.financeiro.Mensalidade;
import net.servehttp.bytecom.persistence.jpa.entity.financeiro.retorno.Registro;
import net.servehttp.bytecom.persistence.jpa.financeiro.MensalidadeJPA;
import net.servehttp.bytecom.persistence.jpa.financeiro.caixa.RegistroJPA;

import javax.inject.Inject;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public class MensalidadeService implements Serializable {

    private static final long serialVersionUID = 8705835474790847188L;
    @Inject
    private MensalidadeJPA mensalidadeJPA;
    @Inject
    private RegistroJPA registroJPA;

    public Mensalidade getNovaMensalidade(Cliente cliente, LocalDate vencimento) {
        Mensalidade m = new Mensalidade();
        m.setDataVencimento(vencimento);
        double valorMensalidade = cliente.getContrato().getPlano().getValorMensalidade();
        if (cliente.getContrato().getEquipamentoWifi() != null) {
            valorMensalidade += 5;
        }
        m.setValor(valorMensalidade);
        m.setCliente(cliente);

        return m;
    }

    public byte[] gerarCarne(List<Mensalidade> mensalidades) {
        Cedente cedente = buscarCedente();
        if (cedente == null) {
            throw new IllegalArgumentException("NÃO EXISTEM NENHUM CEDENTE CADASTRADO");
        } else {
            return GerarBoleto.criarCarneCaixa(mensalidades, cedente);
        }
    }

    private Cedente buscarCedente() {
        return mensalidadeJPA.buscarCedente();
    }

    public void remover(Mensalidade m) {
        mensalidadeJPA.remover(m);
    }


    public void removerMensalidadesAbertasNaoVencida(Cliente cliente) {
        if (cliente.getMensalidades() != null) {
//      TODO: ajustar remoção de mensalidades
//      List<Mensalidade> mensalidades = cliente.getMensalidades().stream().filter(m -> m.getStatus().equals(StatusMensalidade.PENDENTE) && m.getDataVencimento().isAfter(LocalDate.now())).collect(Collectors.toList());
//      mensalidades.forEach(m -> {
//        remover(m);
//        cliente.getMensalidades().remove(m);
//      });

        }
    }


    public List<Mensalidade> buscarMensalidadesPorBoleto(int inicio, int fim) {
        return mensalidadeJPA.buscarMensalidadesPorBoletos(inicio, fim);
    }

    public String statusMensalidade(Mensalidade mensalidade1) {
        String status = mensalidade1.isBaixaManual() ? "BAIXA MANUAL" : "PENDENTE";
        Registro registro = null;
        if(mensalidade1.getNumeroBoleto() != null) {
            registro = registroJPA.buscarPorModalidadeNossoNumero(mensalidade1.getModalidade(), mensalidade1.getNumeroBoleto());
        } else {
            registro = registroJPA.buscarPorModalidadeNossoNumero(mensalidade1.getModalidade(), mensalidade1.getId());
        }

        if(registro != null) {
            status = "PAGO NO BOLETO";
        }
        return status;
    }
}
