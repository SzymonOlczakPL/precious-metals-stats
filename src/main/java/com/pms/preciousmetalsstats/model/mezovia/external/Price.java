package com.pms.preciousmetalsstats.model.mezovia.external;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonTypeName("prices")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Price {

    @JsonProperty("price_retail")
    private Double priceRetail;

    @JsonProperty("price_net")
    private Double priceNet;

    @JsonProperty("price_pos")
    private Double pricePos;
}
