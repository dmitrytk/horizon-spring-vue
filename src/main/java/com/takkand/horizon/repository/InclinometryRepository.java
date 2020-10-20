package com.takkand.horizon.repository;

import com.takkand.horizon.domain.Inclinometry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface InclinometryRepository extends JpaRepository<Inclinometry, Long> {


    @Query(value = "SELECT w.name,i.md,i.azi,i.inc\n" +
            "FROM inclinometry i JOIN wells w ON i.well_id = w.id\n" +
            "WHERE w.id IN (SELECT id FROM wells WHERE field_id = :id)", nativeQuery = true)
    List<Object[]> findFieldInclinometryWithWellNames(Long id);

    @Query(value = "DELETE FROM inclinometry i WHERE i.well_id IN" +
            " (SELECT id FROM wells w WHERE  w.field_id = :id)", nativeQuery = true)
    void deleteFieldInclinometry(Long id);

    @Query(value = "DELETE FROM inclinometry i WHERE i.well_id = :id", nativeQuery = true)
    void deleteWellInclinometry(Long id);
}

