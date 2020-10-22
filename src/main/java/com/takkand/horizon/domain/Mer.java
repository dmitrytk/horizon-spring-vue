package com.takkand.horizon.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "mer", uniqueConstraints = {@UniqueConstraint(columnNames = {"well_id", "date"})})
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class Mer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private Date date;
    private String status;
    private Double rate;
    private Double production;
    @Column(name = "work_days")
    private Integer workDays;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "well_id")
    @JsonIgnore
    private Well well;

}
