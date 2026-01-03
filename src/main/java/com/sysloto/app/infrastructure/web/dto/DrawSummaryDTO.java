package com.sysloto.app.infrastructure.web.dto;

import java.time.LocalDate;
import java.util.List;

public record DrawSummaryDTO(
        String numberCode,
        LocalDate date,
        String scheduleName,
        double totalCollected,
        double totalPayout,
        List<SellerDrawSummaryDTO> sellerSummaries) {
}
