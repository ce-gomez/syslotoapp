package com.sysloto.app.infrastructure.web.dto;

public record SellerFinancialStatDTO(
        Long sellerId,
        String sellerName,
        String sellerInitials,
        double totalSales,
        double totalPayout) {
}
