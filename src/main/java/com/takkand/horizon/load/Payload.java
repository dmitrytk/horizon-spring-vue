package com.takkand.horizon.load;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.takkand.horizon.domain.view.View;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Payload<T extends View> {

    private Long fieldId;
    private List<T> data;


    public boolean isValid() {
        return fieldId != null
                && data != null
                && data.size() > 0;
    }

    public List<T> getValidData() {
        return data.stream().filter(View::isValid).collect(Collectors.toList());
    }
}
