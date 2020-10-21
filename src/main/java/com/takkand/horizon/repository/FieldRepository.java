package com.takkand.horizon.repository;

import com.takkand.horizon.domain.Field;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FieldRepository extends JpaRepository<Field, Long> {

    Field findByName(String name);

    @Modifying
    @Query(value = "DELETE FROM wells w WHERE w.field_id = :id", nativeQuery = true)
    void deleteWells(Long id);

    List<Field> findByNameContaining(String name);

}
