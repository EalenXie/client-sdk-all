package io.github.vo.checkoutform;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by EalenXie on 2022/3/24 14:39
 */
@Getter
@Setter
public class PaidAmount {
    /**
     * 数量
     */
    @JsonProperty("amount")
    private String amount;
    /**
     * 货币
     */
    @JsonProperty("currency")
    private String currency;
}
