package com.takkand.horizon.repository;

import com.takkand.horizon.domain.Field;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Sql("/populate.sql")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FieldRepositoryTest {

    @Autowired
    private FieldRepository repository;

    @Test
    @Order(1)
    void findAllFields() {
        List<Field> fields = repository.findAll();
        fields.forEach(f -> System.out.println(f.getId()));
        assertThat(fields.size()).isEqualTo(2);
    }

    @Test
    @Order(2)
    void findByNameLike() {
        List<Field> fields = repository.findByNameContaining("arich");
        assertThat(fields.size()).isEqualTo(1);
    }


    @Test
    @Order(3)
    void deleteAllFields() {
        repository.deleteAll();
        List<Field> fields = repository.findAll();
        assertThat(fields.size()).isEqualTo(0);
    }
}
