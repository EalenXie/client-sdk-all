package io.github.allegro.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author EalenXie
 * @since 2022/11/21 13:02
 */
@Getter
@RequiredArgsConstructor
public enum AllegroLineItemsSentStatus {
    /**
     * none of line items have tracking number specified
     * 完全没有标发
     */
    NONE("NONE"),
    /**
     * some of line items have tracking number specified
     * 部分标发
     */
    SOME("SOME"),
    /**
     * all of line items have tracking number specified全部标发
     */
    ALL("ALL");


    private final String status;


    public String getStatus() {
        return this.status;
    }
}
