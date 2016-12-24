package br.com.clairtonluz.sicoba.model.entity.financeiro.gerencianet.carnet;

/**
 * Created by clairton on 20/12/16.
 */
public enum StatusCarnet {
    ACTIVE("ativo", "Carnê ativo."),
    EXPIRED("expirado", "Carnê expirado. A data de vencimento da última parcela do carnê foi ultrapassada."),
    CANCELED("canceled", "Carnê cancelado.");

    private final String label;
    private final String description;

    StatusCarnet(String label, String description) {
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
