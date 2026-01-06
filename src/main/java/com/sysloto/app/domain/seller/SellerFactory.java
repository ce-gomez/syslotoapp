package com.sysloto.app.domain.seller;

import com.sysloto.app.domain.investment.LotteryNumberRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@AllArgsConstructor
@Service
public class SellerFactory {
    private final LotteryNumberRepository lotteryNumberRepository;

    public Seller create(String name, String lastname, double factor) {
        var seller = Seller.create(name, lastname, BigDecimal.valueOf(factor));
        lotteryNumberRepository.findAll().forEach(n -> {
            seller.setOrUpdateLimit(n, BigDecimal.valueOf(250));
        });
        return seller;
    }
}
