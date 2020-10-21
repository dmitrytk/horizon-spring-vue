package com.takkand.horizon.repository;

import com.takkand.horizon.domain.FieldCoordinates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface FieldCoordinatesRepository extends JpaRepository<FieldCoordinates, Long> {
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM field_coordinates f WHERE f.field_id = :id", nativeQuery = true)
    void deleteFieldCoordinates(Long id);
}
