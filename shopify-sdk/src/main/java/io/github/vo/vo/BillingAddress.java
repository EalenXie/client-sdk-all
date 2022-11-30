package io.github.vo.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
public class BillingAddress {

    /**
     *
     **/
    @JsonProperty("first_name")
    private String firstName;
    /**
     *
     **/
    @JsonProperty("address1")
    private String address1;
    /**
     *
     **/
    @JsonProperty("phone")
    private String phone;
    /**
     *
     **/
    @JsonProperty("city")
    private String city;
    /**
     *
     **/
    @JsonProperty("zip")
    private String zip;
    /**
     *
     **/
    @JsonProperty("province")
    private String province;
    /**
     *
     **/
    @JsonProperty("country")
    private String country;
    /**
     *
     **/
    @JsonProperty("last_name")
    private String lastName;
    /**
     *
     **/
    @JsonProperty("address2")
    private String address2;
    /**
     *
     **/
    @JsonProperty("company")
    private String company;
    /**
     *
     **/
    @JsonProperty("latitude")
    private BigDecimal latitude;
    /**
     *
     **/
    @JsonProperty("longitude")
    private BigDecimal longitude;
    /**
     *
     **/
    @JsonProperty("name")
    private String name;
    /**
     *
     **/
    @JsonProperty("country_code")
    private String countryCode;
    /**
     *
     **/
    @JsonProperty("province_code")
    private String provinceCode;

}
