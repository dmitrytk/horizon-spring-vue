package com.takkand.horizon.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "inclinometry")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Inclinometry {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "inclinometry_id_seq")
    @SequenceGenerator(name = "inclinometry_id_seq", sequenceName = "inclinometry_id_seq", allocationSize = 1)
    private Long id;

    private Double md;
    private Double inc;
    private Double azi;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "well_id")
    @JsonIgnore
    private Well well;

    public Inclinometry() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Inclinometry that = (Inclinometry) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(md, that.md) &&
                Objects.equals(inc, that.inc) &&
                Objects.equals(azi, that.azi) &&
                Objects.equals(well, that.well);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, md, inc, azi, well);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getMd() {
        return md;
    }

    public void setMd(Double md) {
        this.md = md;
    }

    public Double getInc() {
        return inc;
    }

    public void setInc(Double inc) {
        this.inc = inc;
    }

    public Double getAzi() {
        return azi;
    }

    public void setAzi(Double azi) {
        this.azi = azi;
    }

    public Well getWell() {
        return well;
    }

    public void setWell(Well well) {
        this.well = well;
    }
}
