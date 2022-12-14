package io.github.allegro.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Carrier {
    /**
     * 承运人id
     */
    @JsonProperty("id")
    private String id;
    /**
     * 承运人名称
     */
    @JsonProperty("name")
    private String name;
}