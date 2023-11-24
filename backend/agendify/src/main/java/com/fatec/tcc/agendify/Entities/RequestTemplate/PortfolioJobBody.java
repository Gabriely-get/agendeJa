package com.fatec.tcc.agendify.Entities.RequestTemplate;

import java.util.List;

public record PortfolioJobBody (Long portfolioId, Long jobId, Double price, String name, String description, List<String> serviceImages, String coverImage) {
}
