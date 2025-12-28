package com.sysloto.app.application.service;

import com.sysloto.app.domain.sale.Bill;
import com.sysloto.app.domain.sale.BillRepository;
import com.sysloto.app.domain.sale.LotteryNumber;
import com.sysloto.app.domain.sale.Sale;
import com.sysloto.app.domain.schedule.Schedule;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class LotteryNumberStatsService {
    private final BillRepository billRepository;
    private final ScheduleFinder scheduleFinder;

    /**
     * Calcula el monto total vendido para un número en el turno actual
     */
    public double calculateShiftSales(LotteryNumber lotteryNumber) {
        Schedule currentSchedule = scheduleFinder.findCurrentSchedule().orElse(null);
        if (currentSchedule == null) {
            return 0.0;
        }

        List<Bill> bills = billRepository.findAll();
        return bills.stream()
                .filter(bill -> bill.getSchedule().getScheduleId().equals(currentSchedule.getScheduleId()))
                .filter(bill -> bill.getDate().toLocalDate().equals(LocalDate.now()))
                .flatMap(bill -> bill.getSales().stream())
                .filter(sale -> sale.getLotteryNumber().getNumberId().equals(lotteryNumber.getNumberId()))
                .mapToDouble(Sale::getPrice)
                .sum();
    }

    /**
     * Calcula el monto total vendido para un número en el día actual
     */
    public double calculateDailySales(LotteryNumber lotteryNumber) {
        List<Bill> bills = billRepository.findAll();
        return bills.stream()
                .filter(bill -> bill.getDate().toLocalDate().equals(LocalDate.now()))
                .flatMap(bill -> bill.getSales().stream())
                .filter(sale -> sale.getLotteryNumber().getNumberId().equals(lotteryNumber.getNumberId()))
                .mapToDouble(Sale::getPrice)
                .sum();
    }
}
