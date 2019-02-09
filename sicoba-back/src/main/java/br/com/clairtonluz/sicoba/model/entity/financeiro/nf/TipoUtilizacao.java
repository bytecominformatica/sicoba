package br.com.clairtonluz.sicoba.model.entity.financeiro.nf;

public enum TipoUtilizacao {
    TELEFONIA(1),
    COMUNICACAO_DE_DADOS(2),
    TV_POR_ASSINATURA(3),
    PROVIMENTO_DE_INTERNET(4),
    MULTIMIDIA(5),
    OUTROS(6);

    private final int codigo;

    TipoUtilizacao(int codigo) {
        this.codigo = codigo;
    }

    public int getCodigo() {
        return codigo;
    }
}
