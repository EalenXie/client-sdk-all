package io.github.allegro.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileVO {
    @JsonProperty("name")
    private String name;
    @JsonProperty("uploadedAt")
    private String uploadedAt;
    @JsonProperty("securityVerification")
    private SecurityVerification securityVerification;
}