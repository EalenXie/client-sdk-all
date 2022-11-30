package io.github.allegro.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by EalenXie on 2022/3/18 15:05
 */
@Getter
@Setter
public class Invoices {

    @JsonProperty("invoices")
    private List<Invoice> invoicesList;

    @JsonProperty("hasExternalInvoices")
    private Boolean hasExternalInvoices;

}
