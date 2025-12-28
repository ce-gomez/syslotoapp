package com.sysloto.app.application.service;

import com.sysloto.app.domain.sale.LotteryNumber;
import com.sysloto.app.domain.sale.LotteryNumberRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class RegisterLotteryNumberService {
    private final LotteryNumberRepository lotteryNumberRepository;

    @Transactional
    public LotteryNumber register(String number, double limit) {
        // Validar que el número no exista ya
        if (lotteryNumberRepository.findByNumber(number).isPresent()) {
            throw new IllegalStateException("El número " + number + " ya existe");
        }

        // Validar formato del número (debe ser de 2 dígitos)
        if (!number.matches("\\d{2}")) {
            throw new IllegalArgumentException("El número debe tener exactamente 2 dígitos");
        }

        // Crear y guardar el número
        LotteryNumber lotteryNumber = LotteryNumber.create(number, limit);
        return lotteryNumberRepository.save(lotteryNumber);
    }
}
