package br.com.clairtonluz.sicoba.model.entity.financeiro.gerencianet.charge;

/**
 * Created by clairton on 20/12/16.
 */
public enum StatusCharge {
    NEW("Novo", "Cobrança gerada, aguardando definição da forma de pagamento."),
    WAITING("Aguardando", "Forma de pagamento selecionada, aguardando a confirmação do pagamento."),
    PAID("Pago", "Pagamento confirmado."),
    UNPAID("Não Pago", "Boleto não foi pago até o momento."),
    REFUNDED("Devolvido", "Pagamento devolvido pelo lojista ou pelo intermediador Gerencianet."),
    CONTESTED("Contestado", "Pagamento em processo de contestação."),
    CANCELED("Cancelado", "Cobrança cancelada pelo vendedor ou pelo pagador."),
    LINK("Link", "Cobrança associada a um link de pagamento.");

    private final String label;
    private final String description;

    StatusCharge(String label, String description) {
        this.label = label;
        this.description = description;
    }

    @Override
    public String toString() {
        return label;
    }

    public String getDescription() {
        return description;
    }
}
