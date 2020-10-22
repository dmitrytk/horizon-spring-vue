package com.takkand.horizon.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Data;

import javax.persistence.*;


@Entity
@Table(name = "zones", uniqueConstraints = {@UniqueConstraint(columnNames = {"well_id", "name"})})
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class Zone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Column(name = "top_md")
    @JsonSetter("top_md")
    private Double topMd;

    @Column(name = "bot_md")
    @JsonSetter("bot_md")
    private Double botMd;

    @Column(name = "top_tvd")
    @JsonSetter("top_tvd")
    private Double topTvd;

    @Column(name = "bot_tvd")
    @JsonSetter("bot_tvd")
    private Double botTvd;

    @Column(name = "h")
    @JsonSetter("h")
    private Double h;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "well_id")
    @JsonIgnore
    private Well well;

}
