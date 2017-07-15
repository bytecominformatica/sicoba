package br.com.clairtonluz.sicoba.model.entity.financeiro.gerencianet.carnet;

/**
 * Created by clairton on 20/12/16.
 */
public enum StatusCarnet {
    ACTIVE("ativo", "Carnê ativo."),
    EXPIRED("expirado", "Carnê expirado. A data de vencimento da última parcela do carnê foi ultrapassada."),
    CANCELED("cancelado", "Carnê cancelado."),
    /*
     status que irão valer a partir do dia 14/08/2017.
     Os status acima vão deixar de existir a partir desse dia.
      */
    UP_TO_DATE("Em dia", "O carnê encontra-se em dia."),
    UNPAID("Não pago", "O carnê encontra-se inadimplente."),
    FINISHED("Finalizado", "O carnê está finalizado.");

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
