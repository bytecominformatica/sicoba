package br.com.clairtonluz.bytecom.model.service.financeiro;

import br.com.clairtonluz.bytecom.model.jpa.entity.comercial.Cliente;
import br.com.clairtonluz.bytecom.model.jpa.entity.comercial.Contrato;
import br.com.clairtonluz.bytecom.model.jpa.entity.financeiro.Mensalidade;
import br.com.clairtonluz.bytecom.model.jpa.financeiro.MensalidadeJPA;
import br.com.clairtonluz.bytecom.model.repository.comercial.ContratoRepository;

import javax.inject.Inject;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class MensalidadeService implements Serializable {

    private static final long serialVersionUID = 8705835474790847188L;
    @Inject
    private MensalidadeJPA mensalidadeJPA;
    @Inject
    private ContratoRepository contratoRepository;

    public Mensalidade getNova(Cliente cliente, Date vencimento) {
        Contrato contrato = contratoRepository.findOptionalByCliente(cliente);

        Mensalidade m = new Mensalidade();
        m.setDataVencimento(vencimento);
        double valorMensalidade = contrato.getPlano().getValorMensalidade();
        if (contrato.getEquipamentoWifi() != null) {
            valorMensalidade += 5;
        }
        m.setValor(valorMensalidade);
        m.setCliente(cliente);

        return m;
    }

    public void removerTodosAbertasNaoVencida(List<Mensalidade> mensalidades) {
        if (mensalidades != null) {
//            mensalidades.stream().filter(m -> m.getStatus().equals(StatusMensalidade.PENDENTE)
//                    && m.getDataVencimento().isAfter(LocalDate.now()))
//                    .forEach(m -> {
//                        remove(m);
//                        mensalidades.remove(m);
//                    });
        }
    }

    public void remove(Mensalidade m) {
        mensalidadeJPA.remove(m);
    }

    public List<Mensalidade> buscarPorBoleto(int modalidade, int inicio, int fim) {
        return mensalidadeJPA.buscarMensalidadesPorBoletos(modalidade, inicio, fim);
    }

    public List<Mensalidade> buscarPorCliente(Cliente cliente) {
        return mensalidadeJPA.bucarPorCliente(cliente);
    }

    public List<Mensalidade> buscarMensalidadesAtrasada() {
        return mensalidadeJPA.buscarMensaliadadesAtrasada();
    }

    public void save(Mensalidade mensalidade) {
        mensalidadeJPA.save(mensalidade);
    }
}
