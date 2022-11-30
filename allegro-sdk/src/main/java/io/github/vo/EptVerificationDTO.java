package io.github.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class EptVerificationDTO {
    @JsonProperty("status")
    private String status;
    @JsonProperty("verifiedAt")
    private String verifiedAt;
    @JsonProperty("reasons")
    private List<Reason> reasons;
}