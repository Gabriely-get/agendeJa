package com.fatec.tcc.agendify.Entities.RequestTemplate;

import lombok.Data;
import org.springframework.context.annotation.Bean;

public record PortfolioJobBody (Long portfolioId, Long jobId, Double price, String name, String description) {
}
