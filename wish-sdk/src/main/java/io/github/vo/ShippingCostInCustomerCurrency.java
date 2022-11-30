package io.github.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by EalenXie on 2022/3/24 15:47
 */
@Getter
@Setter
public class ShippingCostInCustomerCurrency {
    @JsonProperty("amount")
    private Double amount;
    @JsonProperty("currency_code")
    private String currencyCode;
}
