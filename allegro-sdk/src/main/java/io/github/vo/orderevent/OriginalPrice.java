package io.github.vo.orderevent;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by EalenXie on 2022/3/24 15:08
 */
@Getter
@Setter
public class OriginalPrice {
    @JsonProperty("amount")
    private String amount;
    @JsonProperty("currency")
    private String currency;
}
