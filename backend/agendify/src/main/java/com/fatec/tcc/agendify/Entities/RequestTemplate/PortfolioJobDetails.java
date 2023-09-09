package com.fatec.tcc.agendify.Entities.RequestTemplate;

import lombok.Data;

public @Data class PortfolioJobDetails {
    private Long id;
    String name;
    Double price;
    String description;
    String categoryName;

}
