package io.github.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BillingEntries {
    @JsonProperty("id")
    private String id;

    @JsonProperty("occurredAt")
    private String occurredAt;

    @JsonProperty("type")
    private BillingType type;

    @JsonProperty("offer")
    private BillingOffer offer;

    @JsonProperty("value")
    private BillingValue value;

    @JsonProperty("tax")
    private BillingTax tax;

    @JsonProperty("balance")
    private BillingBalance balance;

    @JsonProperty("order")
    private IdVO order;
}
