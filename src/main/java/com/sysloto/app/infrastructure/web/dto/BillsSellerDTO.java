package com.sysloto.app.infrastructure.web.dto;

import com.sysloto.app.domain.sale.Bill;
import com.sysloto.app.domain.seller.Seller;

import java.util.List;

public record BillsSellerDTO(
        int count,
        List<Bill> bills
) {
}
