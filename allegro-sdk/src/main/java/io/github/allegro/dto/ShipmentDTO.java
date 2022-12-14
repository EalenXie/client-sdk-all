package io.github.allegro.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by EalenXie on 2022/3/16 10:48
 */
@Getter
@Setter
public class ShipmentDTO {


    /**
     * 运营商id
     */
    @JsonProperty("carrierId")
    private String carrierId;
    /**
     * 运单号
     */
    @JsonProperty("waybill")
    private String waybill;
    /**
     * 运营商名称
     */
    @JsonProperty("carrierName")
    private String carrierName;
    /**
     * 订单列表
     */
    @JsonProperty("lineItems")
    private List<LineItems> lineItems;


}
