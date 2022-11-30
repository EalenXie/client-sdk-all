package io.github.allegro.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SecurityVerification {
    @JsonProperty("status")
    private String status;
    @JsonProperty("verifiedAt")
    private String verifiedAt;
}