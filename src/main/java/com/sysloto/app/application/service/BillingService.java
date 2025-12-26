package com.sysloto.app.application.service;

import com.sysloto.app.domain.sale.Bill;
import com.sysloto.app.domain.sale.BillRepository;
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

    @Transactional
    public Bill registerNewBill(Long sellerId) {
        var seller = sellerRepository.findById(sellerId).get();
        var schedule = scheduleFinder.findCurrentSchedule()
                .orElseThrow(() -> new IllegalStateException("Fuera de turno"));
        var bill = Bill.create(seller, schedule);
        return billRepository.save(bill);
    }

}
