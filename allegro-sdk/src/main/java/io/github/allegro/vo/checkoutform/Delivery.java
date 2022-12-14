package io.github.allegro.vo.checkoutform;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by EalenXie on 2022/3/24 14:39
 */
@Getter
@Setter
public class Delivery {
    /**
     * 地址信息
     */
    @JsonProperty("address")
    private DeliveryAddress address;
    /**
     * 方法
     */
    @JsonProperty("method")
    private Method method;
    /**
     * 接送地点
     */
    @JsonProperty("pickupPoint")
    private PickupPoint pickupPoint;
    /**
     * 成本
     */
    @JsonProperty("cost")
    private PaidAmount cost;
    /**
     * 交付时间
     */
    @JsonProperty("time")
    private Time time;
    @JsonProperty("smart")
    private Boolean smart;
    /**
     * 包裹数量
     */
    @JsonProperty("calculatedNumberOfPackages")
    private Integer calculatedNumberOfPackages;
}
