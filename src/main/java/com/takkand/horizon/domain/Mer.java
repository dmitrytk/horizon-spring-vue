package com.takkand.horizon.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "mer")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Mer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;
    private String status;
    private Double rate;
    private Double production;
    @Column(name = "work_days")
    private Integer workDays;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "well_id")
    @JsonIgnore
    private Well well;

    public Mer() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mer mer = (Mer) o;
        return Objects.equals(id, mer.id) &&
                Objects.equals(date, mer.date) &&
                Objects.equals(status, mer.status) &&
                Objects.equals(rate, mer.rate) &&
                Objects.equals(production, mer.production) &&
                Objects.equals(workDays, mer.workDays) &&
                Objects.equals(well, mer.well);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, status, rate, production, workDays, well);
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Double getProduction() {
        return production;
    }

    public void setProduction(Double production) {
        this.production = production;
    }

    public Integer getWorkDays() {
        return workDays;
    }

    public void setWorkDays(Integer workDays) {
        this.workDays = workDays;
    }

    public Well getWell() {
        return well;
    }

    public void setWell(Well well) {
        this.well = well;
    }
}
