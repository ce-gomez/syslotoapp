package com.sysloto.app.application.service.seller;

import com.sysloto.app.domain.seller.Seller;
import com.sysloto.app.domain.seller.SellerFactory;
import com.sysloto.app.domain.seller.SellerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class RegistrationSellerService {
    private final SellerRepository sellerRepository;
    private final SellerFactory factory;

    @Transactional
    public Seller register(String name, String lastname, double factor) {
        var sel = factory.create(name, lastname, factor);
        return sellerRepository.save(sel);
    }
}
