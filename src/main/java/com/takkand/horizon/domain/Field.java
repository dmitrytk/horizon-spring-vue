package com.takkand.horizon.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "fields")
@JsonIgnoreProperties(ignoreUnknown = true)
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

    public Field() {
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Field field = (Field) o;
        return Objects.equals(id, field.id) &&
                name.equals(field.name) &&
                Objects.equals(type, field.type) &&
                Objects.equals(location, field.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, type, location);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<Well> getWells() {
        return wells;
    }

    public void setWells(List<Well> wells) {
        this.wells = wells;
    }

}
