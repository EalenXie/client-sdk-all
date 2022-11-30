package io.github.allegro.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BillingDTO {

    @JsonProperty("offer.id")
    private String offerId;

    @JsonProperty("order.id")
    private String orderId;

    @JsonProperty("type.id")
    private List<String> typeId;

    @JsonProperty("occurredAt.lte")
    private String occurredAtLte;

    @JsonProperty("occurredAt.gte")
    private String occurredAtGte;

    @JsonProperty("limit")
    private Integer limit;

    @JsonProperty("offset")
    private Integer offset;
}
