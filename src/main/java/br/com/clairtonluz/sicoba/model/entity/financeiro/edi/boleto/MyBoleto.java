package br.com.clairtonluz.sicoba.model.entity.financeiro.edi.boleto;

import br.com.clairtonluz.sicoba.model.entity.extra.BaseEntity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by clairton on 05/10/16.
 */

@Entity
@Table(name = "boleto")
public class MyBoleto extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "boleto_id_seq")
    @SequenceGenerator(name = "boleto_id_seq", sequenceName = "boleto_id_seq")
    private Integer id;

    @Column(name = "data_documento")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataDocumento;
    @Column(name = "data_processamento")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataProcessamento;
    @Column(name = "data_vencimento")
    @Temporal(TemporalType.TIMESTAMP)
    private Date vencimento;

    private String agencia;
    @Column(name = "digito_agencia")
    private String digitoAgencia;
    private String conta;
    @Column(name = "digito_conta")
    private String digitoConta;
    @Column(name = "numero_convenio")
    private String numeroConvenio;
    private String carteira;
    @Column(name = "nosso_numero")
    private String nossoNumero;

    @Column(name = "nome_beneficiario")
    private String nomeBeneficiario;
    @Column(name = "cep_beneficiario")
    private String cepBeneficiario;
    @Column(name = "logradouro_beneficiario")
    private String logradouroBeneficiario;
    @Column(name = "bairro_beneficiario")
    private String bairroBeneficiario;
    @Column(name = "cidade_beneficiario")
    private String cidadeBeneficiario;
    @Column(name = "uf_beneficiario")
    private String ufBeneficiario;


    @Column(name = "nome_pagador")
    private String nomePagador;
    @Column(name = "documento_pagador")
    private String documentoPagador;
    @Column(name = "cep_pagador")
    private String cepPagador;
    @Column(name = "logradouro_pagador")
    private String logradouroPagador;
    @Column(name = "bairro_pagador")
    private String bairroPagador;
    @Column(name = "cidade_pagador")
    private String cidadePagador;
    @Column(name = "uf_pagador")
    private String ufPagador;

    private BigDecimal valor;
    @Column(name = "numero_documento")
    private Integer numeroDocumento;

    @Override
    public Integer getId() {
        return id;
    }

}
