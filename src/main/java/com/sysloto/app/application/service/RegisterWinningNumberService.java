package com.sysloto.app.application.service;

import com.sysloto.app.domain.sale.LotteryNumberRepository;
import com.sysloto.app.domain.sale.WinningNumber;
import com.sysloto.app.domain.sale.WinningNumberRepository;
import com.sysloto.app.domain.schedule.Schedule;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@AllArgsConstructor
public class RegisterWinningNumberService {

    private final WinningNumberRepository winningNumberRepository;
    private final LotteryNumberRepository lotteryNumberRepository;
    private final ScheduleFinder scheduleFinder;

    @Transactional
    public WinningNumber register(String number) {
        // Find current schedule
        Schedule currentSchedule = scheduleFinder.findCurrentSchedule()
                .orElseThrow(() -> new IllegalStateException("No hay un turno activo en este momento."));

        LocalDate today = LocalDate.now();

        // VALIDATION 1: Check if winning number already exists for this shift and date
        if (winningNumberRepository.findByDateAndSchedule(today, currentSchedule).isPresent()) {
            throw new IllegalStateException("Ya se ha registrado un número ganador para este turno hoy.");
        }

        // VALIDATION 2: Check if the number exists in the LotteryNumber registry
        if (lotteryNumberRepository.findByNumber(number).isEmpty()) {
            throw new IllegalArgumentException("El número ingresado no existe en el registro de números jugables.");
        }

        WinningNumber winningNumber = new WinningNumber(number, today, currentSchedule);
        return winningNumberRepository.save(winningNumber);
    }
}
