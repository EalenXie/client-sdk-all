package io.github.marketplace.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


/**
 * @author EalenXie
 * @since 2022/08/04 12:27
 */
@Getter
@Setter
public class OrderLines {

    @JsonProperty("orderLine")
    private List<OrderLine> orderLine;
}