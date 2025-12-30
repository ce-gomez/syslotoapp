package com.sysloto.app.infrastructure.persistence;

import com.sysloto.app.domain.sale.Bill;
import com.sysloto.app.domain.sale.BillRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface JpaBillRepository extends ListCrudRepository<Bill, Long>, BillRepository {
    @Override
    @Query("SELECT b FROM Bill b inner join Seller s on s.sellerId = b.seller.sellerId WHERE s.sellerId = :id")
    List<Bill> findBySellerId(@Param("id") Long sellerId);

    @Override
    @Query("SELECT b FROM Bill b inner join Seller s on s.sellerId = b.seller.sellerId WHERE s.sellerId = :sellerId AND b.schedule.scheduleId = :scheduleId")
    List<Bill> findBySellerSchedule(@Param("sellerId") Long sellerId, @Param("scheduleId") Long scheduleId);

    @Override
    @Query("SELECT b FROM Bill b inner join Seller s on s.sellerId = b.seller.sellerId WHERE s.sellerId = :sellerId AND b.schedule.scheduleId = :scheduleId AND CAST(b.date AS localdate) = :date")
    List<Bill> findBySellerScheduleDate(@Param("sellerId") Long sellerId, @Param("scheduleId") Long scheduleId,
            @Param("date") java.time.LocalDate date);

    @Override
    @Query("SELECT b FROM Bill b WHERE CAST(b.date AS localdate) = :date")
    List<Bill> findByDate(@Param("date") java.time.LocalDate date);

    @Override
    @Query("SELECT b FROM Bill b WHERE CAST(b.date AS localdate) = :date AND b.schedule.scheduleId = :scheduleId")
    List<Bill> findByDateAndSchedule(@Param("date") java.time.LocalDate date, @Param("scheduleId") Long scheduleId);
}