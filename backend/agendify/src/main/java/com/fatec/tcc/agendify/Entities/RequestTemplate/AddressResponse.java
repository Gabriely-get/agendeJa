package com.fatec.tcc.agendify.Entities.RequestTemplate;

public record AddressResponse(
        String cep,
        String bairro,
        String cidade,
        String estado,
        String logradouro,
        String numero,
        String complemento
) {
}
