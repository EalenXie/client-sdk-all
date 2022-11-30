package io.github.amazon.vo.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BuyerTaxInfo {
    @JsonProperty("CompanyLegalName")
    private String companyLegalName;
    @JsonProperty("TaxingRegion")
    private String taxingRegion;
    @JsonProperty("TaxClassifications")
    private List<TaxClassification> taxClassifications;
}