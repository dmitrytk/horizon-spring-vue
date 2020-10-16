package com.takkand.horizon.repository;

import com.takkand.horizon.domain.Mer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MerRepository extends JpaRepository<Mer, Long> {

    @Query(value = "select w.name,m.date, m.status, m.rate, m.production, m.work_days\n" +
            "from mer m join wells w on m.well_id = w.id\n" +
            "where w.id in (select id from wells where field_id = :id)", nativeQuery = true)
    List<Object[]> findFieldMerWithWellNames(Long id);
}
