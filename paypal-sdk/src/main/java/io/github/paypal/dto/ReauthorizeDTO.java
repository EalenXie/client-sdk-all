package io.github.paypal.dto;

import io.github.paypal.vo.Money;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by EalenXie on 2022/11/28 15:20
 */
@Getter
@Setter
public class ReauthorizeDTO {

    private Money amount;

}
