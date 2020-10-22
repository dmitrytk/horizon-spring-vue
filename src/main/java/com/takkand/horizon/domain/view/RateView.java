package com.takkand.horizon.domain.view;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.takkand.horizon.domain.Rate;
import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = true)
@Data
public class RateView extends Rate implements View {

    @JsonSetter("well")
    private String wellName;

    @Override
    public boolean isValid() {
        return wellName != null
                && getDate() != null;
    }
}

