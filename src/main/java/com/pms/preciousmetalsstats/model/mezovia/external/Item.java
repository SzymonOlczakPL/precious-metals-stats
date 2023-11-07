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
@JsonTypeName("items")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Item {
    @JsonProperty("00000-uniw")
    private ItemDetails itemDetails;
}
