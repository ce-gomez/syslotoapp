package com.sysloto.app.infrastructure.persistence;

import com.sysloto.app.domain.schedule.Schedule;
import com.sysloto.app.domain.schedule.ScheduleRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.Optional;

@Repository
public interface JpaScheduleRepository extends ScheduleRepository, ListCrudRepository<Schedule, Long> {

    // Custom query to satisfy the interface method since 'time' is not a property
    // Handling midnight wrap around in SQL is complex, this simple query handles
    // standard hours.
    // Ideally ScheduleFinder logic is better for this.
    @Override
    @Query("SELECT s FROM Schedule s WHERE :time BETWEEN s.specification.start AND s.specification.end")
    Optional<Schedule> findByTime(@Param("time") LocalTime time);
}
