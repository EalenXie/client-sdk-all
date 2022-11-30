package io.github.vo.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Payment {
    @JsonProperty("CurrencyCode")
    private String currencyCode;
    @JsonProperty("Amount")
    private String amount;
}