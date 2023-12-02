package com.fatec.tcc.agendify.Entities.RequestTemplate;

import java.time.LocalTime;
import java.util.List;

public record PortfolioJobBody (
        Long portfolioId,
        Long jobId,
        Double price,
        String name,
        String description,
        LocalTime duration,
        List<String> serviceImages,
        String coverImage,
        Boolean restricted
) {
}
