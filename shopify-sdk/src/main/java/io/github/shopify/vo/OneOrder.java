package io.github.shopify.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OneOrder {
    @JsonProperty("order")
    private Order order;
}
