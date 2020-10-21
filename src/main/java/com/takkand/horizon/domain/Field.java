package com.takkand.horizon.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "fields")
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class Field {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;
    private String type;
    private String location;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "field")
    @JsonIgnore
    private List<Well> wells = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "field")
    @JsonIgnore
    private List<FieldCoordinates> coordinates = new ArrayList<>();


    public void update(Field otherField) {
        if (otherField.getName() != null) setName(otherField.getName());
        if (otherField.getType() != null) setType(otherField.getType());
        if (otherField.getLocation() != null) setLocation(otherField.getLocation());
    }

}
