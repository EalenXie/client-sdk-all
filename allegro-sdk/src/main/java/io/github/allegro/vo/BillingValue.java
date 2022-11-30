package io.github.allegro.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class BillingValue {
    @JsonProperty("amount")
    private String amount;
    @JsonProperty("currency")
    private String currency;
}
