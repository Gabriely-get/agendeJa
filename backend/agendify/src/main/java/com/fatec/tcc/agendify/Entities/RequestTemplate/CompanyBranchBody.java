package com.fatec.tcc.agendify.Entities.RequestTemplate;

import lombok.Data;

import java.util.List;

public @Data class CompanyBranchBody {
    private String fantasyName;
    private Long userId;
    private CepApi address;
    private Long category;
    private List<Long> subCategories;
    private Boolean is24Hours;
    private String description;

    public CompanyBranchBody(String fantasyName, Long userId, CepApi address) {
        this.setAddress(address);
        this.setFantasyName(fantasyName);
        this.setUserId(userId);
    }

    public CompanyBranchBody(String fantasyName, Long id, CepApi address, Long category, List<Long> subCategories, String description) {
        this.setAddress(address);
        this.setFantasyName(fantasyName);
        this.setUserId(id);
        this.setCategory(category);
        this.setSubCategories(subCategories);
        this.setDescription(description);
    }

    public CompanyBranchBody() {

    }

    public void setIs24Hours(Boolean is24Hours) {
        this.is24Hours = is24Hours;
    }
}
