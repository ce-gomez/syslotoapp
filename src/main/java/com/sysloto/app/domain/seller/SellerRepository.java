package com.sysloto.app.domain.seller;

import com.sysloto.app.domain.BaseDomainRepository;

import java.util.List;
import java.util.Optional;

public interface SellerRepository extends BaseDomainRepository<Seller> {
    Optional<Seller> findById(Long id);
    List<Seller> findByName(String name);
    Seller findByInvestmentId(Long investment);
}
