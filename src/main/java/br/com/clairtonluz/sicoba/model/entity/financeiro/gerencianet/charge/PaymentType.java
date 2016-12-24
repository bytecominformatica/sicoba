package br.com.clairtonluz.sicoba.model.entity.financeiro.gerencianet.charge;

/**
 * Created by clairton on 21/12/16.
 */
public enum PaymentType {
    ALL("Todos"),
    BANKING_BILLET("Boleto bancário"),
    CREDIT_CARD("Cartão de crédito");

    private final String label;

    PaymentType(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}
