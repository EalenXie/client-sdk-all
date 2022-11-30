package io.github.vo.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentExecutionDetail {
    @JsonProperty("Payment")
    private Payment payment;
    @JsonProperty("PaymentMethod")
    private String paymentMethod;
}