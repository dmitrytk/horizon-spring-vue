package com.takkand.horizon.domain.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.takkand.horizon.domain.Mer;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class MerView extends Mer implements View {

    @JsonSetter("well")
    private String wellName;


    public boolean isValid() {
        return wellName != null
                && getDate() != null;
    }
}

