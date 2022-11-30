package io.github.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BillingTypeVO {

    @JsonProperty("id")
    private String id;

    @JsonProperty("description")
    private String description;
}
