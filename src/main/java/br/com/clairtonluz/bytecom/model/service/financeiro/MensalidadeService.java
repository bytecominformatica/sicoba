package br.com.clairtonluz.bytecom.model.service.financeiro;

import br.com.clairtonluz.bytecom.model.jpa.entity.comercial.Cliente;
import br.com.clairtonluz.bytecom.model.jpa.entity.comercial.Contrato;
import br.com.clairtonluz.bytecom.model.jpa.entity.financeiro.Mensalidade;
import br.com.clairtonluz.bytecom.model.jpa.entity.financeiro.StatusMensalidade;
import br.com.clairtonluz.bytecom.model.repository.comercial.ContratoRepository;
import br.com.clairtonluz.bytecom.model.repository.financeiro.MensalidadeRepository;
import br.com.clairtonluz.bytecom.util.DateUtil;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class MensalidadeService implements Serializable {

    private static final long serialVersionUID = 8705835474790847188L;
    @Inject
    private MensalidadeRepository mensalidadeRepository;
    @Inject
    private ContratoRepository contratoRepository;

    public Mensalidade getNova(Integer clienteId) {
        Contrato contrato = contratoRepository.findOptionalByCliente_id(clienteId);
        Date vencimento = DateUtil.toDate(LocalDate.now().plusMonths(1).withDayOfMonth(contrato.getVencimento()));
        return getNova(contrato.getCliente(), vencimento);
    }

    public Mensalidade getNova(Cliente cliente, Date vencimento) {
        Contrato contrato = contratoRepository.findOptionalByCliente_id(cliente.getId());

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


    public List<Mensalidade> buscarPorBoleto(Integer modalidade, Integer inicio, Integer fim) {
        return mensalidadeRepository.findByNumeroBoletoBetweenAndModalidade(inicio, fim, modalidade);
    }

    public List<Mensalidade> buscarPorCliente(Integer clienteId) {
        return mensalidadeRepository.findByCliente_idOrderByDataVencimentoAsc(clienteId);
    }

    public List<Mensalidade> buscarMensalidadesAtrasada() {
        return mensalidadeRepository
                .findByStatusAndDataVencimentoLessThanOrderByDataVencimentoAsc(StatusMensalidade.PENDENTE, new Date());
    }

    @Transactional
    public Mensalidade save(Mensalidade mensalidade) {
        return mensalidadeRepository.save(mensalidade);
    }

    @Transactional
    public void remove(Integer id) {
        Mensalidade m = mensalidadeRepository.findBy(id);
        mensalidadeRepository.remove(m);
    }

    public Mensalidade buscarPorId(Integer id) {
        return mensalidadeRepository.findBy(id);
    }
}
