package io.github.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItem {
    @JsonProperty("orderItemId")
    private String orderItemId;
    @JsonProperty("quantity")
    private Integer quantity;
}