package io.github.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class BillingTax {
    @JsonProperty("percentage")
    private String percentage;
    @JsonProperty("annotation")
    private String annotation;
}
