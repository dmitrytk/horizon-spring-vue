package com.takkand.horizon.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;


@Entity
@Table(name = "rates")
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
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


}
