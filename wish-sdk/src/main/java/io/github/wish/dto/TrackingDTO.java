package io.github.wish.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by EalenXie on 2022/3/18 16:56
 */
@Getter
@Setter
public class TrackingDTO {


    @JsonProperty("tracking_number")
    private String trackingNumber;
    @JsonProperty("origin_country")
    private String originCountry;
    @JsonProperty("ship_note")
    private String shipNote;
    @JsonProperty("shipping_provider")
    private String shippingProvider;
}
