package com.takkand.horizon.domain.view;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.hibernate.annotations.Immutable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Immutable
@Table(name = "zones_view")
@Data
public class ZoneView implements View {

    @Id
    private Long id;
    private String name;

    @JsonProperty("top_md")
    private Double topMd;

    @JsonProperty("bot_md")
    private Double botMd;

    @JsonProperty("top_tvd")
    private Double topTvd;

    @JsonProperty("bot_tvd")
    private Double botTvd;

    @JsonProperty("h")
    private Double h;

    @Column(name = "well")
    @JsonProperty("well")
    private String wellName;

    @Override
    @JsonIgnore
    public boolean isValid() {
        return wellName != null
                && getName() != null;
    }
}
