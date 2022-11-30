package io.github.amazon.vo.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by EalenXie on 2022/2/22 15:39
 */
@Getter
@Setter
public class AmazonBuyerInfo {

    @JsonProperty("AmazonOrderId")
    private String amazonOrderId;
    @JsonProperty("BuyerEmail")
    private String buyerEmail;
    @JsonProperty("BuyerName")
    private String buyerName;
    @JsonProperty("BuyerCounty")
    private String buyerCounty;
    @JsonProperty("BuyerTaxInfo")
    private BuyerTaxInfo buyerTaxInfo;
    @JsonProperty("PurchaseOrderNumber")
    private String purchaseOrderNumber;
}
