package io.github.fruugo.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * @author 无敌暴龙战士
 * @since 2022/08/10 12:27
 */
@Getter
@Setter
public class ShipmentLine {
    /**
     * Fruugo 的内部产品 ID
     */
    private String fruugoProductId;
    /**
     * Fruugo 的内部 Sku id
     */
    private String fruugoSkuId;
    /**
     * 装运的物品数量
     */
    private String quantity;
}
