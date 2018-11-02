package br.com.clairtonluz.sicoba.repository.financeiro.nf;

import br.com.clairtonluz.sicoba.model.entity.financeiro.nf.NfeItem;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class NFeItemSpec {

    public static Specification<NfeItem> dataPrestacaoBetween(LocalDate begin, LocalDate end) {
        return (Specification<NfeItem>) (root, query, cb) -> cb.between(root.get("nfe").get("dataPrestacao"), begin, end);
    }
}
