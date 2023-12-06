package com.fatec.tcc.agendify.Entities.RequestTemplate;

import java.util.List;

public record PortfolioJobResponse(
        Long id,
        String name,
        Double price,
        String description,
        String duration,
        Long portfolioId,
        String category,
        String jobCategory,
        String owner,
        Boolean restricted,
        String coverImage,
        List<String> images

) {
}
