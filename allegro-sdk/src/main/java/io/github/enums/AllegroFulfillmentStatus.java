package io.github.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author EalenXie
 * @since 2022/11/21 12:58
 */
@Getter
@RequiredArgsConstructor
public enum AllegroFulfillmentStatus {
    NEW("NEW"),

    PROCESSING("PROCESSING"),

    READY_FOR_SHIPMENT("READY_FOR_SHIPMENT"),

    READY_FOR_PICKUP("READY_FOR_PICKUP"),

    SENT("SENT"),

    PICKED_UP("PICKED_UP"),

    CANCELLED("CANCELLED"),

    SUSPENDED("SUSPENDED");


    private final String status;


    public String getStatus() {
        return this.status;
    }
}
