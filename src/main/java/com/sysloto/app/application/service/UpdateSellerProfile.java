package com.sysloto.app.application.service;

import com.sysloto.app.domain.seller.SellerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Map;

@AllArgsConstructor
@Service
public class UpdateSellerProfile {
    private final SellerRepository sellerRepository;

    @Transactional
    public void updateProfile(Long id, String name, String lastname, double factor) {
        var seller = sellerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Vendedor no encontrado: " + id));
        seller.setName(name);
        seller.setLastname(lastname);
        seller.setFactor(BigDecimal.valueOf(factor));

        sellerRepository.save(seller);
    }
}
