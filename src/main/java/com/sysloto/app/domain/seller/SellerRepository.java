package com.sysloto.app.domain.seller;

import java.util.List;
import java.util.Optional;

public interface SellerRepository {
    Optional<Seller> findById(Long id);
    List<Seller> findByName(String name);
    Seller findByBillId(Long billId);
    Seller save(Seller seller);
    void delete(Seller seller);
    List<Seller> findAll();
}
