package io.github.allegro.vo.checkoutform;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by EalenXie on 2022/3/24 14:39
 */
@Getter
@Setter
public class Summary {
    /**
     * 共计支付
     */
    @JsonProperty("totalToPay")
    private PaidAmount totalToPay;
}
