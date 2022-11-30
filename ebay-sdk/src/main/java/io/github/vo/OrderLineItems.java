package io.github.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by EalenXie on 2022/5/31 14:19
 */
@Getter
@Setter
public class OrderLineItems {
    @JsonProperty("lineItemId")
    private String lineItemId;
    @JsonProperty("feeBasisAmount")
    private Amount feeBasisAmount;
    @JsonProperty("marketplaceFees")
    private List<MarketplaceFees> marketplaceFees;
}
