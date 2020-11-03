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
@Table(name = "mer_view")
@Data
public class MerView implements View {

    @Id
    private Long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private Date date;
    private String status;
    private Double rate;
    private Double production;
    @Column(name = "work_days")
    @JsonProperty("work_days")
    private Integer workDays;

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

