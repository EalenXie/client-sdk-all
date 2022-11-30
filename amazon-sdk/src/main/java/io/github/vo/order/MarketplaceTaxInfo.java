package io.github.vo.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MarketplaceTaxInfo {
    @JsonProperty("TaxClassifications")
    private List<TaxClassification> taxClassifications;
}