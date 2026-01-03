package com.sysloto.app.infrastructure.persistence;

import com.sysloto.app.domain.lottery.WinningNumber;
import com.sysloto.app.domain.lottery.WinningNumberRepository;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaWinningNumberRepository extends WinningNumberRepository, ListCrudRepository<WinningNumber, Long> {
}
