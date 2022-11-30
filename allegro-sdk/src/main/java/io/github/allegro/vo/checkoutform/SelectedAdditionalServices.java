package io.github.allegro.vo.checkoutform;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by EalenXie on 2022/3/24 14:39
 */
@Getter
@Setter
public class SelectedAdditionalServices {
    @JsonProperty("definitionId")
    private String definitionId;
    @JsonProperty("name")
    private String name;
    @JsonProperty("price")
    private PaidAmount price;
    @JsonProperty("quantity")
    private Integer quantity;
}
