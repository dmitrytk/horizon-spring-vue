package com.takkand.horizon.domain.view;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;
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

    @Column(name = "well")
    @JsonSetter("well")
    private String wellName;

    @Override
    @JsonIgnore
    public boolean isValid() {
        return wellName != null
                && getName() != null;
    }
}
