package br.com.clairtonluz.bytecom.model.service.comercial;

import br.com.clairtonluz.bytecom.model.jpa.comercial.ContratoJPA;
import br.com.clairtonluz.bytecom.model.jpa.entity.comercial.Contrato;
import br.com.clairtonluz.bytecom.model.service.CrudService;

import javax.inject.Inject;
import java.time.LocalDate;
import java.util.List;

public class ContratoService extends CrudService {

    private static final long serialVersionUID = 8705835474790847188L;
    @Inject
    private ContratoJPA contratoJPA;

    public ContratoService() {
    }

    public ContratoService(ContratoJPA contratoJPA) {
        this.contratoJPA = contratoJPA;
    }

    public Contrato buscarPorId(int id) {
        return contratoJPA.buscarPorId(id);
    }

    public List<Contrato> buscarTodosInstaladoEsseMes() {
        LocalDate now = LocalDate.now();
        LocalDate from = now.withDayOfMonth(1);
        LocalDate to = now.withDayOfMonth(now.lengthOfMonth());
        return contratoJPA.buscarTodosPorDataInstalacao(from, to);
    }

}
