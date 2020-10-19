package com.takkand.horizon.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;


@Entity
@Table(name = "rates")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Rate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;
    private Double rate;
    @Column(name = "dynamic")
    private Double dynamicLevel;
    @Column(name = "static")
    private Double staticLevel;
    private Double pressure;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "well_id")
    @JsonIgnore
    private Well well;

    public Rate() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rate rate1 = (Rate) o;
        return Objects.equals(id, rate1.id) &&
                Objects.equals(date, rate1.date) &&
                Objects.equals(rate, rate1.rate) &&
                Objects.equals(dynamicLevel, rate1.dynamicLevel) &&
                Objects.equals(staticLevel, rate1.staticLevel) &&
                Objects.equals(pressure, rate1.pressure) &&
                Objects.equals(well, rate1.well);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, rate, dynamicLevel, staticLevel, pressure, well);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Double getDynamicLevel() {
        return dynamicLevel;
    }

    public void setDynamicLevel(Double dynamicLevel) {
        this.dynamicLevel = dynamicLevel;
    }

    public Double getStaticLevel() {
        return staticLevel;
    }

    public void setStaticLevel(Double staticLevel) {
        this.staticLevel = staticLevel;
    }

    public Double getPressure() {
        return pressure;
    }

    public void setPressure(Double pressure) {
        this.pressure = pressure;
    }

    public Well getWell() {
        return well;
    }

    public void setWell(Well well) {
        this.well = well;
    }
}
