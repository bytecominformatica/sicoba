package br.com.clairtonluz.sicoba.service.financeiro;

import br.com.clairtonluz.sicoba.exception.ConflitException;
import br.com.clairtonluz.sicoba.model.entity.comercial.Cliente;
import br.com.clairtonluz.sicoba.model.entity.comercial.Contrato;
import br.com.clairtonluz.sicoba.model.entity.comercial.StatusCliente;
import br.com.clairtonluz.sicoba.model.entity.financeiro.StatusTitulo;
import br.com.clairtonluz.sicoba.model.entity.financeiro.Titulo;
import br.com.clairtonluz.sicoba.model.pojo.financeiro.Carne;
import br.com.clairtonluz.sicoba.model.pojo.financeiro.gerencianet.CarnetPojo;
import br.com.clairtonluz.sicoba.repository.comercial.ClienteRepository;
import br.com.clairtonluz.sicoba.repository.comercial.ContratoRepository;
import br.com.clairtonluz.sicoba.repository.financeiro.CedenteRepository;
import br.com.clairtonluz.sicoba.repository.financeiro.TituloRepository;
import br.com.clairtonluz.sicoba.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TituloService {

    @Autowired
    private TituloRepository tituloRepository;
    @Autowired
    private CedenteRepository cedenteRepository;
    @Autowired
    private ContratoRepository contratoRepository;
    @Autowired
    private ClienteRepository clienteRepository;

    public Titulo getNovo(Integer clienteId) {
        Contrato contrato = contratoRepository.findOptionalByCliente_id(clienteId);
        Date vencimento = DateUtil.toDate(LocalDate.now().plusMonths(1).withDayOfMonth(contrato.getVencimento()));
        return getNovo(contrato.getCliente(), vencimento);
    }

    private Titulo getNovo(Cliente cliente, Date vencimento) {
        Contrato contrato = contratoRepository.findOptionalByCliente_id(cliente.getId());

        Titulo m = new Titulo();
        m.setDataVencimento(vencimento);
        double valorTitulo = contrato.getPlano().getValor();
        if (contrato.getEquipamentoWifi() != null) {
            valorTitulo += 5;
            m.setDesconto(5);
        }
        m.setValor(valorTitulo);
        m.setCliente(cliente);

        return m;
    }

    public List<Titulo> buscarPorBoleto(Integer inicio, Integer fim) {
        return tituloRepository.findByNumeroBoletoBetween(inicio, fim);
    }

    public List<Titulo> buscarPorCliente(Integer clienteId) {
        return tituloRepository.findByCliente_idOrderByDataVencimentoDesc(clienteId);
    }

    public List<Titulo> buscarVencidos() {
        return tituloRepository
                .findByStatusAndDataVencimentoLessThanAndCliente_statusNotOrderByDataVencimentoAsc(StatusTitulo.PENDENTE, new Date(), StatusCliente.CANCELADO);
    }

    @Transactional
    public Titulo save(Titulo titulo) {

        if (existe(titulo)) {
            throw new ConflitException("Titulo de número " + titulo.getNumeroBoleto() + " já existe.");
        }

        return tituloRepository.save(titulo);
    }

    public List<Titulo> save(List<Titulo> titulos) {
        List<Integer> existentes = buscarNumeroBoletoSeExistir(titulos);
        if (existentes.isEmpty()) {
            for (Titulo titulo : titulos) {
                tituloRepository.save(titulo);
            }
        } else {
            throw new ConflitException("Já existe titulos com os seguintes números de boleto: " + existentes.toString());
        }
        return titulos;
    }

    public List<Integer> buscarNumeroBoletoSeExistir(List<Titulo> titulosRegistradas) {
        List<Integer> existentes = new ArrayList<>();
        titulosRegistradas.forEach((it) -> {
            if (it.getNumeroBoleto() != null && it.getNumeroBoleto() > 0) {
                Titulo tituloExistente = tituloRepository.findOptionalByNumeroBoleto(it.getNumeroBoleto());
                if (tituloExistente != null) {
                    existentes.add(tituloExistente.getNumeroBoleto());
                }
            }
        });

        return existentes;
    }

    @Transactional
    public void remove(Integer id) {
        Titulo m = tituloRepository.findOne(id);
        tituloRepository.delete(m);
    }

    public Titulo buscarPorId(Integer id) {
        return tituloRepository.findOne(id);
    }

    @Transactional
    public List<Titulo> criarCarne(Carne carne) {
        List<Titulo> titulos = new ArrayList<>();

        if (titulosNaoRegistrado(carne.getModalidade(), carne.getBoletoInicio(), carne.getBoletoFim())) {

            Date data = carne.getDataInicio();
            Cliente cliente = clienteRepository.findOne(carne.getClienteId());

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

    public List<Titulo> criarTitulos(CarnetPojo carnetPojo) {
        List<Titulo> titulos = new ArrayList<>();
        Cliente cliente = clienteRepository.findOne(carnetPojo.getClienteId());

        LocalDate vencimento = DateUtil.toLocalDateTime(carnetPojo.getDataInicio()).toLocalDate();
        for (int i = 1; i <= carnetPojo.getQuantidadeParcela(); i++) {
            Titulo titulo = new Titulo();
            titulo.setCliente(cliente);
            titulo.setValor(carnetPojo.getValor());
            titulo.setDesconto(carnetPojo.getDesconto());
            titulo.setDataVencimento(DateUtil.toDate(vencimento));
            titulo.setStatus(StatusTitulo.PENDENTE);
            titulos.add(titulo);

            vencimento = vencimento.plusMonths(1);
        }

        save(titulos);

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
        boolean existe = false;
        if (titulo.getNumeroBoleto() != null) {
            Titulo t = tituloRepository.findOptionalByNumeroBoleto(titulo.getNumeroBoleto());
            existe = t != null && !t.getId().equals(titulo.getId());
        }
        return existe;
    }

    private boolean titulosNaoRegistrado(Integer modalidade, Integer inicio, Integer fim) {
        boolean validos = true;
        List<Titulo> listTitulos = buscarPorBoleto(inicio, fim);
        if (!listTitulos.isEmpty()) {
            validos = false;
            StringBuilder sb = new StringBuilder("Os seguintes boletos já estão cadastrados");
            for (Titulo titulo : listTitulos) {
                sb.append(" : " + titulo.getNumeroBoleto());
            }
            throw new ConflitException(sb.toString());
        }
        return validos;
    }

    public Titulo buscarPorBoleto(Integer numeroBoleto) {
        return tituloRepository.findOptionalByNumeroBoleto(numeroBoleto);
    }

    public List<Titulo> buscarPorDataOcorreciaStatus(Date inicio, Date fim, StatusTitulo status) {
        if (status == null) {
            return tituloRepository.findByDataOcorrenciaBetween(inicio, fim);
        } else {
            return tituloRepository.findByDataOcorrenciaBetweenAndStatus(inicio, fim, status);
        }
    }

    public List<Titulo> buscarPorDataVencimentoStatus(Date inicio, Date fim, StatusTitulo status) {
        if (status == null) {
            return tituloRepository.findByDataVencimentoBetween(inicio, fim);
        } else {
            return tituloRepository.findByDataVencimentoBetweenAndStatus(inicio, fim, status);
        }
    }

    public List<Titulo> buscarNaoVencidosPorCliente(Cliente cliente) {
        return tituloRepository.findByClienteAndStatusAndDataVencimentoGreaterThan(cliente, StatusTitulo.PENDENTE, new Date());
    }

    public byte[] criarBoletos(List<Integer> titulos) {

        return new byte[0];
    }
}
