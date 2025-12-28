package com.sysloto.app.infrastructure.config;

import com.sysloto.app.domain.sale.LotteryNumber;
import com.sysloto.app.domain.sale.LotteryNumberRepository;
import com.sysloto.app.domain.schedule.SaleSpecification;
import com.sysloto.app.domain.schedule.Schedule;
import com.sysloto.app.domain.schedule.ScheduleRepository;
import lombok.AllArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.time.LocalTime;

@Configuration
@AllArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final ScheduleRepository scheduleRepository;
    private final LotteryNumberRepository lotteryNumberRepository;

    @Override
    public void run(String @NonNull ... args) {
        if (scheduleRepository.count() == 0) {
            // Diurno: 08:00 - 16:00
            scheduleRepository.save(Schedule.create("Diurno",
                    new SaleSpecification(LocalTime.of(8, 0), LocalTime.of(11, 30))));

            // Vespertino: 16:00 - 23:59
            scheduleRepository.save(Schedule.create("Vespertino",
                    new SaleSpecification(LocalTime.of(13, 0), LocalTime.of(14, 30))));

            // Noche: 00:00 - 08:00
            scheduleRepository.save(Schedule.create("Noche",
                    new SaleSpecification(LocalTime.of(18, 0), LocalTime.of(20, 30))));
        }

        // Esta comprobación protege tus datos de reinicios
        if (lotteryNumberRepository.count() == 0) {
            for (int i = 0; i <= 99; i++) {
                String formattedNumber = String.format("%02d", i);
                lotteryNumberRepository.save(LotteryNumber.create(formattedNumber, 250.0));
            }
        }
    }
}
