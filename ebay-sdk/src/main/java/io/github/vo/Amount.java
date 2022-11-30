package io.github.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by EalenXie on 2022/5/31 14:19
 */
@Getter
@Setter
public class Amount {
    @JsonProperty("value")
    private String value;
    @JsonProperty("currency")
    private String currency;
    @JsonProperty("convertedFromValue")
    private String convertedFromValue;
    @JsonProperty("convertedFromCurrency")
    private String convertedFromCurrency;
    @JsonProperty("exchangeRate")
    private String exchangeRate;
}
