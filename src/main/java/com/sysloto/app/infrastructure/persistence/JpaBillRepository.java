package com.sysloto.app.infrastructure.persistence;

import com.sysloto.app.domain.sale.Bill;
import com.sysloto.app.domain.sale.BillRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaBillRepository extends ListCrudRepository<Bill, Long>, BillRepository {
    @Override
    @Query("SELECT b FROM Bill b inner join Seller s on s.sellerId = b.seller.sellerId WHERE s.sellerId = :id")
    List<Bill> findBySellerId(Long sellerId);
}