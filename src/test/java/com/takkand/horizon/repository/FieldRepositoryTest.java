package com.takkand.horizon.repository;

import com.takkand.horizon.domain.Field;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class FieldRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private FieldRepository fieldRepository;

    @Test
    public void whenFindById_thenReturnField() {
        // given
        Field field = new Field();
        field.setName("Carichan");
        entityManager.persist(field);
        entityManager.flush();

        // when
        Field found = fieldRepository.findById(1L).get();

        // then
        assertThat(found.getName())
                .isEqualTo(field.getName());
    }
}
