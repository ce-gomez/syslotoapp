package com.sysloto.app.infrastructure.web.dto;

import com.sysloto.app.domain.sale.LotteryNumber;
import com.sysloto.app.domain.sale.Sale;

public record SaleDTO(
        Long saleId,
        double price,
        double factor,
        double disbursement,
        LotteryNumber lotteryNumber
) {
    public static SaleDTO fromEntity(Sale sale) {
        return new SaleDTO(
                sale.getSaleId(),
                sale.getPrice(),
                sale.getFactor(),
                sale.getPrice() * sale.getFactor(),
                sale.getLotteryNumber());
    }
}
