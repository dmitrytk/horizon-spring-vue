package com.takkand.horizon.domain.view;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.hibernate.annotations.Immutable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Immutable
@Data
public class RandomView {
    @Id
    @JsonProperty("Идентификатор")
    private Long id;

    @JsonProperty("Номер")
    private String name;
}
