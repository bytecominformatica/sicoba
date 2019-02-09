package br.com.clairtonluz.sicoba.model.entity.comercial;

public enum TipoPessoa {
    PF("Pessoa Física", 5307), PJ("PessoaJurídica", 5303);

    private final String descricao;
    private final int cfop;

    TipoPessoa(String descricao, int cfop) {
        this.descricao = descricao;
        this.cfop = cfop;
    }

    public String getDescricao() {
        return descricao;
    }

    public int getCfop() {
        return cfop;
    }
}
