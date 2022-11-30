package io.github.amazon.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by EalenXie on 2022/2/22 17:49
 */
@Getter
@Setter
public class ShipmentDTO {


    @JsonProperty("marketplaceId")
    private String marketplaceId;
    @JsonProperty("shipmentStatus")
    private String shipmentStatus;
    @JsonProperty("orderItems")
    private List<OrderItem> orderItems;


}
