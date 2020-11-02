package com.takkand.horizon.domain.view;

import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Data;
import org.hibernate.annotations.Immutable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Immutable
@Table(name = "inclinometry_view")
@Data
public class IncView implements View {

    @Id
    Long id;
    @Column(name = "well_name")
    @JsonSetter("well")
    private String wellName;
    private Double md;
    private Double inc;
    private Double azi;

    @Override
    public boolean isValid() {
        return wellName != null
                && getMd() != null;
    }
}
