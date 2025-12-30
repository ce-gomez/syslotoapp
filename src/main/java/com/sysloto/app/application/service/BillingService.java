package com.sysloto.app.application.service;

import com.sysloto.app.domain.sale.Bill;
import com.sysloto.app.domain.sale.BillRepository;
import com.sysloto.app.domain.sale.LotteryNumberRepository;
import com.sysloto.app.domain.seller.SellerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class BillingService {
    private final BillRepository billRepository;
    private final ScheduleFinder scheduleFinder;
    private final SellerRepository sellerRepository;
    private final LotteryNumberRepository lotteryNumberRepository;

    @Transactional
    public Bill registerNewBill(Long sellerId) {
        var seller = sellerRepository.findById(sellerId).get();
        var schedule = scheduleFinder.findCurrentSchedule()
                .orElseThrow(() -> new IllegalStateException("Fuera de turno"));
        var bill = Bill.create(seller, schedule);
        return billRepository.save(bill);
    }

    @Transactional
    public Bill createNewSale(Long billId, String number, double price) {
        var bill = billRepository.findByBillId(billId).orElseThrow();
        var lotteryNumber = lotteryNumberRepository.findByNumber(number)
                .orElseThrow(() -> new IllegalArgumentException("Numero no encontrado"));
        bill.addSale(lotteryNumber, price, bill.getSeller().getFactor());
        return billRepository.save(bill);
    }

    @Transactional
    public void deleteBill(Long billId) {
        billRepository.deleteById(billId);
    }

    @Transactional
    public Bill deleteSale(Long billId, Long saleId) {
        var bill = billRepository.findByBillId(billId).orElseThrow();
        bill.getSales().removeIf(sale -> sale.getSaleId().equals(saleId));
        return billRepository.save(bill);
    }

}
