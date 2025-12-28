package com.sysloto.app.application.service;

import com.sysloto.app.domain.sale.LotteryNumber;
import com.sysloto.app.domain.sale.LotteryNumberRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class UpdateLotteryNumberLimitService {
    private final LotteryNumberRepository lotteryNumberRepository;

    @Transactional
    public void updateLimit(Long numberId, double newLimit) {
        LotteryNumber lotteryNumber = lotteryNumberRepository.findByNumberId(numberId)
                .orElseThrow(() -> new IllegalArgumentException("Número no encontrado con ID: " + numberId));

        if (newLimit < 0) {
            throw new IllegalArgumentException("El límite no puede ser negativo");
        }

        lotteryNumber.setLimit(newLimit);
        lotteryNumberRepository.save(lotteryNumber);
    }
}
