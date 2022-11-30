package io.github.vo.orderevent;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by EalenXie on 2022/3/24 15:08
 */
@Getter
@Setter
public class Buyer {
    @JsonProperty("id")
    private String id;
    @JsonProperty("email")
    private String email;
    @JsonProperty("login")
    private String login;
    @JsonProperty("guest")
    private Boolean guest;
}
