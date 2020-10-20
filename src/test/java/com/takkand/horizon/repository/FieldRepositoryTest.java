package com.takkand.horizon.repository;

import com.takkand.horizon.domain.Field;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Sql("classpath:populate.sql")
public class FieldRepositoryTest {

    @Autowired
    private FieldRepository fieldRepository;

    @Test
    void findAllFields() {
        List<Field> fields = fieldRepository.findAll();
        assertThat(fields.size()).isEqualTo(2);
    }


}
