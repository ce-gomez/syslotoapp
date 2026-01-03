package com.sysloto.app.infrastructure.web.dto;

import com.sysloto.app.domain.seller.Seller;

public record SellerDTO(
        Long sellerId,
        String name,
        String lastname,
        double factor,
        String initials
) {
    public static SellerDTO fromEntity(Seller seller) {
        return new SellerDTO(seller.getSellerId(), seller.getName(), seller.getLastname(), seller.getFactor().doubleValue(), seller.getName().charAt(0) + "" + seller.getLastname().charAt(0));
    }
}
