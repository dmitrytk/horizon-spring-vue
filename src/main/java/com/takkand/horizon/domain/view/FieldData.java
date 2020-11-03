package com.takkand.horizon.domain.view;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.hibernate.annotations.Immutable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Immutable
@Table(name = "field_data")
@Data
public class FieldData {

    @Id
    private Long id;
    private String name;
    @JsonProperty("count")
    private Integer wellssCount;
}
