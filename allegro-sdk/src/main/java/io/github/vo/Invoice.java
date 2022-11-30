package io.github.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Invoice {
    @JsonProperty("id")
    private String id;
    @JsonProperty("invoiceNumber")
    private String invoiceNumber;
    @JsonProperty("createdAt")
    private String createdAt;
    @JsonProperty("file")
    private FileVO file;
    @JsonProperty("eptVerification")
    private EptVerificationDTO eptVerification;


}