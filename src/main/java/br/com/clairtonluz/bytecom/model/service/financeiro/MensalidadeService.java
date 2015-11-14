package br.com.clairtonluz.bytecom.model.service.financeiro;

import br.com.clairtonluz.bytecom.model.jpa.entity.comercial.Cliente;
import br.com.clairtonluz.bytecom.model.jpa.entity.financeiro.Mensalidade;
import br.com.clairtonluz.bytecom.model.jpa.entity.financeiro.StatusMensalidade;
import br.com.clairtonluz.bytecom.model.jpa.financeiro.MensalidadeJPA;
import br.com.clairtonluz.bytecom.model.service.CrudService;

import javax.inject.Inject;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class MensalidadeService extends CrudService {

    private static final long serialVersionUID = 8705835474790847188L;
    @Inject
    private MensalidadeJPA mensalidadeJPA;

    public MensalidadeService() {
    }

    public MensalidadeService(MensalidadeJPA mensalidadeJPA) {
        this.mensalidadeJPA = mensalidadeJPA;
    }

    public Mensalidade getNova(Cliente cliente, LocalDate vencimento) {
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

    public void removerTodosAbertasNaoVencida(Cliente cliente) {
        if (cliente.getMensalidades() != null) {
            List<Mensalidade> mensalidades = cliente.getMensalidades().stream().filter(m -> m.getStatus().equals(StatusMensalidade.PENDENTE) && m.getDataVencimento().isAfter(LocalDate.now())).collect(Collectors.toList());
            mensalidades.forEach(m -> {
                remove(m);
                cliente.getMensalidades().remove(m);
            });

        }
    }

    public List<Mensalidade> buscarPorBoleto(int modalidade, int inicio, int fim) {
        return mensalidadeJPA.buscarMensalidadesPorBoletos(modalidade, inicio, fim);
    }

}
