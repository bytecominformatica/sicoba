package br.com.clairtonluz.sicoba.repository.financeiro.nf;

import br.com.clairtonluz.sicoba.model.entity.financeiro.gerencianet.charge.StatusCharge;
import br.com.clairtonluz.sicoba.model.entity.financeiro.nf.NfeItem;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class NFeItemSpec {

    public static Specification<NfeItem> dataPrestacaoBetween(LocalDate begin, LocalDate end) {
        return (Specification<NfeItem>) (root, query, cb) -> cb.between(root.get("nfe").get("dataPrestacao"), begin, end);
    }

    public static Specification<NfeItem> gerencianetAccountIdEqual(Integer gerencianetAccountId) {
        return (Specification<NfeItem>) (root, query, cb) -> cb.equal(root.get("charge").get("gerencianetAccount"), gerencianetAccountId);
    }

    public static Specification<NfeItem> statusChargeEqual(StatusCharge status) {
        return (Specification<NfeItem>) (root, query, cb) -> cb.equal(root.get("charge").get("status"), status);
    }
}
