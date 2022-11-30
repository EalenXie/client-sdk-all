package io.github.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Reason {
    @JsonProperty("id")
    private String id;
    @JsonProperty("message")
    private String message;
}