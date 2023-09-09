package com.fatec.tcc.agendify.Entities.RequestTemplate;

import lombok.Data;

import java.io.Serializable;

public @Data class CepApi implements Serializable {
    private String cep;
    private String logradouro;
    private String complemento;
    private String bairro;
    private String localidade;
    private String uf;
    private String ibge;
    private String gia;
    private String ddd;
    private String siafi;

    private String numero;
}
