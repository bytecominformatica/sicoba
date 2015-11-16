package br.com.clairtonluz.bytecom.model.service.comercial;

import br.com.clairtonluz.bytecom.model.jpa.entity.comercial.Contrato;
import br.com.clairtonluz.bytecom.model.repository.comercial.ContratoRepository;
import br.com.clairtonluz.bytecom.model.service.CrudService;

import javax.inject.Inject;
import java.time.LocalDate;
import java.util.List;

public class ContratoService extends CrudService {

    private static final long serialVersionUID = 8705835474790847188L;
    @Inject
    private ContratoRepository contratoRepository;

    public List<Contrato> buscarTodosInstaladoEsseMes() {
        LocalDate now = LocalDate.now();
        LocalDate from = now.withDayOfMonth(1);
        LocalDate to = now.withDayOfMonth(now.lengthOfMonth());
        List<Contrato> result = contratoRepository.findByDataInstalacaoBetween(from, to);
        return result;
    }

}
