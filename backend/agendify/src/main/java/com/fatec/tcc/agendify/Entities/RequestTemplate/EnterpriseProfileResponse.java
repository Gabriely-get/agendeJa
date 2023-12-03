package com.fatec.tcc.agendify.Entities.RequestTemplate;

import java.util.List;

public record EnterpriseProfileResponse(
        String name,
        String description,
        List<PortfolioJobResponse> jobs,
        String profileImage,
        String coverImage,
        AddressResponse address,
        List<BussinesHourDataResponse> businessHours
) {
}
