package com.sysloto.app.infrastructure.web.dto;

public record SellerDrawSummaryDTO(
        String sellerName,
        double collected,
        double payout) {
}
