package com.takkand.horizon.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Data;

import javax.persistence.*;
import java.sql.Date;


@Entity
@Table(name = "rates")
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class Rate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private Date date;
    private Double rate;
    @Column(name = "dynamic")
    @JsonSetter("dynamic")
    private Double dynamicLevel;
    @Column(name = "static")
    @JsonSetter("static")
    private Double staticLevel;
    private Double pressure;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "well_id")
    @JsonIgnore
    private Well well;


}
