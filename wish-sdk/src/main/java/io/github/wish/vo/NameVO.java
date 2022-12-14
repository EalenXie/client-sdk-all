package io.github.wish.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by EalenXie on 2022/3/18 16:35
 */
@Getter
@Setter
public class NameVO {

    @JsonProperty("name")
    private String name;
}
