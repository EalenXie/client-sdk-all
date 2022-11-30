package io.github.wish.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by EalenXie on 2022/3/24 15:47
 */
@Getter
@Setter
public class ProductShippingPrice {
    @JsonProperty("amount")
    private Double amount;
    @JsonProperty("currency_code")
    private String currencyCode;
}
