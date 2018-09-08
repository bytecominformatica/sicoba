package br.com.clairtonluz.sicoba.model.entity.financeiro.nf;

public enum TipoAssinante {
    COMERCIAL_OU_INDUSTRIAL(1),
    PODER_PUBLICO(2),
    RESIDENCIAL_OU_PESSOA_FISICA(3),
    PUBLICO(4),
    SEMI_PUBLICO(5),
    OUTROS(6);

    private final int codigo;

    TipoAssinante(int codigo) {
        this.codigo = codigo;
    }

    public int getCodigo() {
        return codigo;
    }
}
