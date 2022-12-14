package io.github.allegro.vo.checkoutform;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by EalenXie on 2022/3/24 14:39
 */
@Getter
@Setter
public class Time {
    /**
     * 首次交付尝试的保证日期
     */
    @JsonProperty("guaranteed")
    private Guaranteed guaranteed;
}
