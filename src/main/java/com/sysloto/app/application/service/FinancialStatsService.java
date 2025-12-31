package com.sysloto.app.application.service;

import com.sysloto.app.domain.sale.Bill;
import com.sysloto.app.domain.sale.BillRepository;
import com.sysloto.app.domain.sale.Sale;
import com.sysloto.app.domain.sale.WinningNumber;
import com.sysloto.app.domain.sale.WinningNumberRepository;
import com.sysloto.app.infrastructure.web.dto.WinningNumberDetailDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class FinancialStatsService {

        private final BillRepository billRepository;
        private final WinningNumberRepository winningNumberRepository;

        public double calculateTotalDailySales() {
                return getDailyBills().stream()
                                .flatMap(bill -> bill.getSales().stream())
                                .mapToDouble(Sale::getPrice)
                                .sum();
        }

        public List<WinningNumberDetailDTO> getWinningDetails() {
                LocalDate today = LocalDate.now();
                List<WinningNumber> winningNumbers = winningNumberRepository.findAllByDate(today);

                List<Bill> dailyBills = getDailyBills();

                return winningNumbers.stream()
                                .map(winningNumber -> {
                                        double payoutForThisSchedule = dailyBills.stream()
                                                        .filter(bill -> bill.getSchedule().getScheduleId()
                                                                        .equals(winningNumber.getSchedule()
                                                                                        .getScheduleId()))
                                                        .flatMap(bill -> bill.getSales().stream())
                                                        .filter(sale -> sale.getLotteryNumber().getNumber()
                                                                        .equals(winningNumber.getNumber()))
                                                        .mapToDouble(Sale::getPayout) // Using price * factor
                                                        .sum();

                                        return new WinningNumberDetailDTO(
                                                        winningNumber.getNumber(),
                                                        winningNumber.getSchedule().getName(),
                                                        payoutForThisSchedule);
                                })
                                .toList();
        }

        public double calculateTotalPayout() {
                return getWinningDetails().stream()
                                .mapToDouble(WinningNumberDetailDTO::totalPayout)
                                .sum();
        }

        public List<com.sysloto.app.infrastructure.web.dto.SellerFinancialStatDTO> getSellerStats() {
                LocalDate today = LocalDate.now();
                List<Bill> dailyBills = getDailyBills();
                List<WinningNumber> winningNumbers = winningNumberRepository.findAllByDate(today);

                java.util.Map<Long, List<Bill>> billsBySeller = dailyBills.stream()
                                .collect(java.util.stream.Collectors
                                                .groupingBy(bill -> bill.getSeller().getSellerId()));

                return billsBySeller.entrySet().stream()
                                .map(entry -> {
                                        com.sysloto.app.domain.seller.Seller seller = entry.getValue().get(0)
                                                        .getSeller();
                                        double totalSales = entry.getValue().stream()
                                                        .flatMap(bill -> bill.getSales().stream())
                                                        .mapToDouble(Sale::getPrice)
                                                        .sum();

                                        double totalPayout = entry.getValue().stream()
                                                        .mapToDouble(bill -> bill.getSales().stream()
                                                                        .filter(sale -> winningNumbers.stream()
                                                                                        .anyMatch(wn -> wn
                                                                                                        .getNumber()
                                                                                                        .equals(sale
                                                                                                                        .getLotteryNumber()
                                                                                                                        .getNumber())
                                                                                                        &&
                                                                                                        wn.getSchedule().getScheduleId()
                                                                                                                        .equals(bill
                                                                                                                                        .getSchedule()
                                                                                                                                        .getScheduleId())))
                                                                        .mapToDouble(Sale::getPayout)
                                                                        .sum())
                                                        .sum();

                                        String initials = seller.getName().substring(0, 1)
                                                        + seller.getLastname().substring(0, 1);
                                        return new com.sysloto.app.infrastructure.web.dto.SellerFinancialStatDTO(
                                                        seller.getSellerId(),
                                                        seller.getName() + " " + seller.getLastname(),
                                                        initials,
                                                        totalSales,
                                                        totalPayout);
                                })
                                .toList();

        }

        private List<Bill> getDailyBills() {
                return billRepository.findAll().stream()
                                .filter(bill -> bill.getDate().toLocalDate().equals(LocalDate.now()))
                                .toList();
        }
}
