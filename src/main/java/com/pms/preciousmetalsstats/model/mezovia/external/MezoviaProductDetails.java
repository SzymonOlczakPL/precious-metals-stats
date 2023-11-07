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
@JsonTypeName("sizes")
@JsonIgnoreProperties(ignoreUnknown = true)
public class MezoviaProductDetails {
    private Long id;

    private String name;

    @JsonProperty("firm")
    private Company company;

    @JsonProperty("taxes")
    private Tax tax;

    private String unit;

    private Long amount;

    @JsonProperty("items")
    private Item item;
}
