package com.sysloto.app.infrastructure.web.dto;

public record InvestmentDTO(
        String numberCode,
        String amount,
        String limit
) {
}
