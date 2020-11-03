package com.takkand.horizon.repository;

import com.takkand.horizon.domain.Inclinometry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;


public interface InclinometryRepository extends JpaRepository<Inclinometry, Long> {

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM inclinometry i WHERE i.well_id IN" +
            " (SELECT id FROM wells w WHERE  w.field_id = :id)", nativeQuery = true)
    void deleteFieldInclinometry(Long id);


    @Modifying
    @Transactional
    @Query(value = "DELETE FROM inclinometry i WHERE i.well_id in " +
            "(SELECT w.id FROM wells w where w.field_id = :fieldId and w.name in (:wellNames) )"
            , nativeQuery = true)
    void deleteInclinometryByWellNames(Long fieldId, List<String> wellNames);
}

