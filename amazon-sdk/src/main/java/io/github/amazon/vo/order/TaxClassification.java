package io.github.amazon.vo.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaxClassification {
    @JsonProperty("Name")
    private String name;
    @JsonProperty("Value")
    private String value;
}