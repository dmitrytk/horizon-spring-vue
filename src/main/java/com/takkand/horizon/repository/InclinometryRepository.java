package com.takkand.horizon.repository;

import com.takkand.horizon.domain.Inclinometry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface InclinometryRepository extends JpaRepository<Inclinometry, Long> {


    @Query(value = "select w.name,i.md,i.azi,i.inc\n" +
            "from inclinometry i join wells w on i.well_id = w.id\n" +
            "where w.id in (select id from wells where field_id = :id)", nativeQuery = true)
    List<Object[]> findFieldInclinometryWithWellNames(Long id);
}

