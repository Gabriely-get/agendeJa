package com.fatec.tcc.agendeja.Entities.RequestTemplate;

import lombok.Data;

public @Data class PortfolioJobBody {
    private Long portfolioId;
    private Long jobId;
    private Double price;
    String name;
    private String description;
//    private Long imageId;
//    private MultipartFile file;
}
