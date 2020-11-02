package com.takkand.horizon.domain.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.takkand.horizon.domain.Inclinometry;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Immutable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@Entity
@Immutable
@Table(name = "inclinometry_view")
public class InclinometryView extends Inclinometry implements View {

    @JsonSetter("well")
    @Column(name = "well_name")
    private String wellName;

    public boolean isValid() {
        return wellName != null
                && getMd() != null;
    }
}
