package com.takkand.horizon.domain.load;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.takkand.horizon.domain.Mer;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MerLoad {

    private String field;
    private List<MerData> mer;

    public boolean isValid() {
        return field != null
                && mer != null
                && mer.size() > 0;
    }


    public static class MerData extends Mer {
        @JsonSetter("well")
        private String wellName;

        public String getWellName() {
            return wellName;
        }

        public void setWellName(String wellName) {
            this.wellName = wellName;
        }

        public boolean isValid() {
            return wellName != null
                    && getDate() != null;
        }

    }
}
