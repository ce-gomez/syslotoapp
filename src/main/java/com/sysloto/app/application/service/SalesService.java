package com.sysloto.app.application.service;

import com.sysloto.app.domain.investment.Investment;
import com.sysloto.app.domain.investment.InvestmentRepository;
import com.sysloto.app.domain.investment.InvestmentService;
import com.sysloto.app.domain.investment.LotteryNumberRepository;
import com.sysloto.app.domain.seller.SellerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@AllArgsConstructor
@Service
public class SalesService {
        private final InvestmentRepository investmentRepository;
        private final SellerRepository sellerRepository;
        private final ScheduleFinder scheduleFinder;
        private final LotteryNumberRepository lotteryNumberRepository;
        private final InvestmentService investmentService;

        @Transactional
        public Investment createNewSale(Long seller, String number, double amount) {
                var schedule = scheduleFinder.findCurrentSchedule()
                                .orElseThrow(() -> new IllegalStateException("No hay turno activo en este momento."));
                var sel = sellerRepository.findById(seller)
                                .orElseThrow(() -> new IllegalArgumentException(
                                                "No existe el vendedor seleccionado: " + seller));
                var lot = lotteryNumberRepository.findByNumberCode(number)
                                .orElseThrow(() -> new IllegalStateException(
                                                "Numero de lotería no encontrado: " + number));

                var inv = investmentService.invest(lot, amount, schedule, sel);
                investmentRepository.save(inv);

                return inv;
        }

        @Transactional
        public void deleteSale(UUID investmentId) {
                investmentRepository.findById(investmentId).ifPresent(investmentRepository::delete);
        }
}
