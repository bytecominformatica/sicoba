package br.com.clairtonluz.bytecom.model.service.financeiro;

import br.com.clairtonluz.bytecom.model.jpa.entity.comercial.Cliente;
import br.com.clairtonluz.bytecom.model.jpa.entity.comercial.Contrato;
import br.com.clairtonluz.bytecom.model.jpa.entity.financeiro.StatusTitulo;
import br.com.clairtonluz.bytecom.model.jpa.entity.financeiro.Titulo;
import br.com.clairtonluz.bytecom.model.repository.comercial.ClienteRepository;
import br.com.clairtonluz.bytecom.model.repository.comercial.ContratoRepository;
import br.com.clairtonluz.bytecom.model.repository.financeiro.TituloRepository;
import br.com.clairtonluz.bytecom.pojo.financeiro.Carne;
import br.com.clairtonluz.bytecom.util.DateUtil;
import br.com.clairtonluz.bytecom.util.MensagemException;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TituloService implements Serializable {

    private static final long serialVersionUID = 8705835474790847188L;
    @Inject
    private TituloRepository tituloRepository;
    @Inject
    private ContratoRepository contratoRepository;
    @Inject
    private ClienteRepository clienteRepository;

    public Titulo getNovo(Integer clienteId) {
        Contrato contrato = contratoRepository.findOptionalByCliente_id(clienteId);
        Date vencimento = DateUtil.toDate(LocalDate.now().plusMonths(1).withDayOfMonth(contrato.getVencimento()));
        return getNovo(contrato.getCliente(), vencimento);
    }

    public Titulo getNovo(Cliente cliente, Date vencimento) {
        Contrato contrato = contratoRepository.findOptionalByCliente_id(cliente.getId());

        Titulo m = new Titulo();
        m.setDataVencimento(vencimento);
        double valorTitulo = contrato.getPlano().getValorTitulo();
        if (contrato.getEquipamentoWifi() != null) {
            valorTitulo += 5;
        }
        m.setValor(valorTitulo);
        m.setCliente(cliente);

        return m;
    }

    public void removerTodosAbertasNaoVencida(List<Titulo> titulos) {
        if (titulos != null) {
//            titulos.stream().filter(m -> m.getStatus().equals(StatusTitulo.PENDENTE)
//                    && m.getDataVencimento().isAfter(LocalDate.now()))
//                    .forEach(m -> {
//                        remove(m);
//                        titulos.remove(m);
//                    });
        }
    }


    public List<Titulo> buscarPorBoleto(Integer inicio, Integer fim) {
        return tituloRepository.findByNumeroBoletoBetween(inicio, fim);
    }

    public List<Titulo> buscarPorCliente(Integer clienteId) {
        return tituloRepository.findByCliente_idOrderByDataVencimentoAsc(clienteId);
    }

    public List<Titulo> buscarVencidos() {
        return tituloRepository
                .findByStatusAndDataVencimentoLessThanOrderByDataVencimentoAsc(StatusTitulo.PENDENTE, new Date());
    }

    @Transactional
    public Titulo save(Titulo titulo) throws MensagemException {

        if (existe(titulo)) {
            throw new MensagemException("Titulo de número " + titulo.getNumeroBoleto() + " já existe.");
        }

        return tituloRepository.save(titulo);
    }

    public List<Titulo> save(List<Titulo> titulos) throws MensagemException {
        List<Integer> existentes = buscarNumeroBoletoSeExistir(titulos);
        if (existentes.isEmpty()) {
            for (Titulo titulo : titulos) {
                tituloRepository.save(titulo);
            }
        } else {
            throw new MensagemException("Já existe titulos com os seguintes números de boleto: " + existentes.toString());
        }
        return titulos;
    }

    public List<Integer> buscarNumeroBoletoSeExistir(List<Titulo> titulosRegistradas) {
        List<Integer> existentes = new ArrayList<>();
        titulosRegistradas.forEach((it) -> {
            Titulo tituloExistente = tituloRepository.findOptionalByNumeroBoleto(it.getNumeroBoleto());
            if (tituloExistente != null) {
                existentes.add(tituloExistente.getNumeroBoleto());
            }
        });

        return existentes;
    }

    @Transactional
    public void remove(Integer id) {
        Titulo m = tituloRepository.findBy(id);
        tituloRepository.remove(m);
    }

    public Titulo buscarPorId(Integer id) {
        return tituloRepository.findBy(id);
    }

    @Transactional
    public List<Titulo> criarCarne(Carne carne) throws MensagemException {
        List<Titulo> titulos = new ArrayList<>();

        if (titulosNaoRegistrado(carne.getModalidade(), carne.getBoletoInicio(), carne.getBoletoFim())) {

            Date data = carne.getDataInicio();
            Cliente cliente = clienteRepository.findBy(carne.getClienteId());

            if (carne.getBoletoInicio() < carne.getBoletoFim()) {
                for (int i = carne.getBoletoInicio(); i <= carne.getBoletoFim(); i++) {
                    Titulo titulo = criarTitulo(cliente, data, carne.getModalidade(), i, carne.getValor(), carne.getDesconto());
                    data = DateUtil.plusMonth(data, 1);
                    titulos.add(titulo);
                }
            } else {
                for (int i = carne.getBoletoInicio(); i >= carne.getBoletoFim(); i--) {
                    Titulo titulo = criarTitulo(cliente, data, carne.getModalidade(), i, carne.getValor(), carne.getDesconto());
                    data = DateUtil.plusMonth(data, 1);
                    titulos.add(titulo);
                }
            }

            save(titulos);
        }
        return titulos;
    }

    private Titulo criarTitulo(Cliente cliente, Date c, Integer modalidade, Integer numeroBoleto,
                               Double valor, Double desconto) {
        Titulo titulo = getNovo(cliente, c);
        titulo.setModalidade(modalidade);
        titulo.setNumeroBoleto(numeroBoleto);
        titulo.setDesconto(desconto);
        titulo.setValor(valor);
        return titulo;
    }

    private boolean existe(Titulo titulo) {
        Titulo t = tituloRepository.findOptionalByNumeroBoleto(titulo.getNumeroBoleto());
        return t != null && t.getId() != titulo.getId();
    }

    private boolean titulosNaoRegistrado(Integer modalidade, Integer inicio, Integer fim) throws MensagemException {
        boolean validos = true;
        List<Titulo> listTitulos = buscarPorBoleto(inicio, fim);
        if (!listTitulos.isEmpty()) {
            validos = false;
            StringBuilder sb = new StringBuilder("Os seguintes boletos já estão cadastrados");
            for (Titulo titulo : listTitulos) {
                sb.append(" : " + titulo.getNumeroBoleto());
            }
            throw new MensagemException(sb.toString());
        }
        return validos;
    }

    public Titulo buscarPorBoleto(Integer numeroBoleto) {
        return tituloRepository.findOptionalByNumeroBoleto(numeroBoleto);
    }
}
