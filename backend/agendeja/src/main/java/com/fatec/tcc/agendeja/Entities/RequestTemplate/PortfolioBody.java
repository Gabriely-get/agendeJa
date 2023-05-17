package com.fatec.tcc.agendeja.Entities.RequestTemplate;

import lombok.Data;

import java.util.List;

public @Data class PortfolioBody {
    private Long categoryId;
    private Long companyBranchId;
    private Long subCategoryId;
    private List<Long> subcategories;

    //for delete subcategory from portfolio

    private Long userId;
    private Long subcategoryId;
}
