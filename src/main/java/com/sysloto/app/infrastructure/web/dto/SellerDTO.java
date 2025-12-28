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
        return new SellerDTO(seller.sellerId, seller.name, seller.lastname, seller.factor, seller.name.charAt(0) + "" + seller.lastname.charAt(0));
    }
}
