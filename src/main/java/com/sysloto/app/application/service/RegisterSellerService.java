package com.sysloto.app.application.service;

import com.sysloto.app.domain.seller.Seller;
import com.sysloto.app.domain.seller.SellerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class RegisterSellerService {
    private final SellerRepository sellerRepository;

    @Transactional
    public Seller register(String name, String lastname, double factor) {
        var sel = Seller.create(name, lastname, factor);
        return sellerRepository.save(sel);
    }
}
