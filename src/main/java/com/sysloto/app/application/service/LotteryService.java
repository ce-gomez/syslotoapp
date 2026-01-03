package com.sysloto.app.application.service;

import com.sysloto.app.domain.investment.LotteryNumberRepository;
import com.sysloto.app.domain.lottery.WinningNumber;
import com.sysloto.app.domain.lottery.WinningNumberRepository;
import com.sysloto.app.domain.schedule.Schedule;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@AllArgsConstructor
public class LotteryService {
    private final WinningNumberRepository winningNumberRepository;
    private final LotteryNumberRepository lotteryNumberRepository;
    private final ScheduleFinder scheduleFinder;

    @Transactional
    public WinningNumber register(String number) {
        Schedule currentSchedule = scheduleFinder.findCurrentSchedule()
                .orElseThrow(() -> new IllegalStateException("No hay un turno activo en este momento."));

        LocalDate today = LocalDate.now();

        if (winningNumberRepository.findByDateAndSchedule(today, currentSchedule).isPresent()) {
            throw new IllegalStateException("Ya se ha registrado un número ganador para este turno hoy.");
        }

        var lottery = lotteryNumberRepository.findByNumberCode(number)
                .orElseThrow(() -> new IllegalArgumentException("El número ingresado no existe."));

        WinningNumber winningNumber = WinningNumber.create(lottery, today, currentSchedule);
        return winningNumberRepository.save(winningNumber);
    }
}
