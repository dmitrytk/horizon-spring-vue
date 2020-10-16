package com.takkand.horizon.repository;

import com.takkand.horizon.domain.Rate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RateRepository extends JpaRepository<Rate, Long> {

    @Query(value = "select w.name,r.date, r.rate, r.dynamic, r.static, r.pressure\n" +
            "from rates r join wells w on r.well_id = w.id\n" +
            "where w.id in (select id from wells where field_id = :id)", nativeQuery = true)
    List<Object[]> findFieldRatesWithWellNames(Long id);
}
