package com.takkand.horizon.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "wells")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Well {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "wells_id_seq")
    @SequenceGenerator(name = "wells_id_seq", sequenceName = "wells_id_seq", allocationSize = 1)
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "field_id")
    @JsonIgnore
    private Field field;

    public Well() {
    }

    public List<Mer> getMer() {
        return mer;
    }

    public void setMer(List<Mer> mer) {
        this.mer = mer;
    }

    public List<Rate> getRates() {
        return rates;
    }

    public void setRates(List<Rate> rates) {
        this.rates = rates;
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

    public String getPad() {
        return pad;
    }

    public void setPad(String pad) {
        this.pad = pad;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getAlt() {
        return alt;
    }

    public void setAlt(Double alt) {
        this.alt = alt;
    }

    public Double getBottom() {
        return bottom;
    }

    public void setBottom(Double bottom) {
        this.bottom = bottom;
    }

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public List<Inclinometry> getInclinometry() {
        return inclinometry;
    }

    public void setInclinometry(List<Inclinometry> inclinometry) {
        this.inclinometry = inclinometry;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }
}
