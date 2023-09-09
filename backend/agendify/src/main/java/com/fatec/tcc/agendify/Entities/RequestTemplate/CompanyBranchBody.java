package com.fatec.tcc.agendify.Entities.RequestTemplate;

import lombok.Data;

import java.util.List;

public @Data class CompanyBranchBody {
    private String fantasyName;
    private Long userId;
    private CepApi address;
    //optional
    private Long category;
    //optional
    private List<Long> subCategories;

    public CompanyBranchBody(String fantasyName, Long userId, CepApi address) {
        this.setAddress(address);
        this.setFantasyName(fantasyName);
        this.setUserId(userId);
    }

    public CompanyBranchBody(String fantasyName, Long id, CepApi address, Long category, List<Long> subCategories) {
        this.setAddress(address);
        this.setFantasyName(fantasyName);
        this.setUserId(id);
        this.setCategory(category);
        this.setSubCategories(subCategories);
    }

    public CompanyBranchBody() {

    }
}
