package com.takkand.horizon.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "wells")
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class Well {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String pad;
    private String type;
    private String status;
    private Double alt;
    private Double bottom;
    private Double x;
    private Double y;
    private Double lat;
    private Double lng;

    @OneToMany(mappedBy = "well", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Inclinometry> inclinometry;

    @OneToMany(mappedBy = "well", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Mer> mer;

    @OneToMany(mappedBy = "well", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Rate> rates;

    @OneToMany(mappedBy = "well", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Zone> zones;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "field_id")
    @JsonIgnore
    private Field field;

    public void update(Well otherWell) {
        if (otherWell.getName() != null) setName(otherWell.getName());
        if (otherWell.getPad() != null) setPad(otherWell.getPad());
        if (otherWell.getType() != null) setName(otherWell.getName());
        if (otherWell.getStatus() != null) setStatus(otherWell.getStatus());
        if (otherWell.getAlt() != null) setAlt(otherWell.getAlt());
        if (otherWell.getBottom() != null) setBottom(otherWell.getBottom());
        if (otherWell.getX() != null) setX(otherWell.getX());
        if (otherWell.getY() != null) setY(otherWell.getY());
        if (otherWell.getLat() != null) setLat(otherWell.getLat());
        if (otherWell.getLng() != null) setLng(otherWell.getLng());
    }

}
