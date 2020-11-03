package com.takkand.horizon.domain.view;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.hibernate.annotations.Immutable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;

@Entity
@Immutable
@Table(name = "rates_view")
@Data
public class RateView implements View {

    @Id
    private Long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private Date date;
    private Double rate;
    @Column(name = "dynamic")
    @JsonProperty("dynamic")
    private Double dynamicLevel;
    @Column(name = "static")
    @JsonProperty("static")
    private Double staticLevel;
    private Double pressure;

    @Column(name = "well")
    @JsonProperty("well")
    private String wellName;

    @Override
    @JsonIgnore
    public boolean isValid() {
        return wellName != null
                && getDate() != null;
    }
}


