package com.sysloto.app.infrastructure.web.dto;

import com.sysloto.app.domain.sale.LotteryNumber;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LotteryNumberDTO {
    private Long numberId;
    private String number;
    private double limit;
    private double shiftSales;
    private double dailySales;

    public static LotteryNumberDTO fromEntity(LotteryNumber lotteryNumber, double shiftSales, double dailySales) {
        return new LotteryNumberDTO(
                lotteryNumber.getNumberId(),
                lotteryNumber.getNumber(),
                lotteryNumber.getLimit(),
                shiftSales,
                dailySales);
    }

    public double getShiftRemaining() {
        return limit - shiftSales;
    }

    public double getDailyRemaining() {
        return limit - dailySales;
    }

    public double getShiftPercentage() {
        return limit > 0 ? (shiftSales / limit) * 100 : 0;
    }

    public double getDailyPercentage() {
        return limit > 0 ? (dailySales / limit) * 100 : 0;
    }
}
