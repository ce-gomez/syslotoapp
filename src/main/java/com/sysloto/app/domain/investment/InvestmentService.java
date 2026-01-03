package com.sysloto.app.domain.investment;

import com.sysloto.app.domain.schedule.Schedule;
import com.sysloto.app.domain.seller.Seller;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@AllArgsConstructor
@Service
public class InvestmentService {
    private final InvestmentRepository investmentRepository;

    public Investment invest(LotteryNumber number, double amount, Schedule schedule, Seller seller) {
        var tempInv = Investment.create(amount, seller.getFactor().doubleValue(), number);
        tempInv.setSchedule(schedule);
        tempInv.setSeller(seller);

        if (!schedule.getSpecification().isSatisfiedBy(tempInv)) {
            throw new IllegalStateException("La venta no cumple con las reglas del turno actual.");
        }

        // TODO: implements specification
        var investments = investmentRepository
                .findByNumberAndScheduleAndDate(number.getLotteryNumberId(), schedule.getScheduleId(), LocalDate.now());
        var accrual = investments
                .stream()
                .filter(inv -> inv.getSeller().equals(seller))
                .toList()
                .stream().map(Investment::getAmount).reduce(0.0, Double::sum);

        var limit = seller.getLimits().stream()
                .filter(l -> l.getLimited().equals(number))
                .findFirst()
                .orElseThrow(IllegalStateException::new);

        if ((accrual + amount) > limit.getAmount().doubleValue()) {
            throw new IllegalStateException("Se ha alcanzado el límite de venta para el número: " + number.getNumberCode());
        }

        return tempInv;
    }
}
