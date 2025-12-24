package com.sysloto.app.infrastructure.persistence;

import com.sysloto.app.domain.seller.Seller;
import com.sysloto.app.domain.seller.SellerRepository;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaSellerRepository extends SellerRepository, ListCrudRepository<Seller, Long> {
}
