package com.sysloto.app.infrastructure.persistence;

import com.sysloto.app.domain.seller.Seller;
import com.sysloto.app.domain.seller.SellerRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaSellerRepository extends SellerRepository, ListCrudRepository<Seller, Long> {
    @Override @Query("SELECT s FROM Seller s inner join Investment b on b.seller.sellerId = s.sellerId WHERE b.investmentId = :id")
    Seller findByInvestmentId(@Param("id") Long id);
}
