package com.pms.preciousmetalsstats.model.mezovia.external;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonTypeName("firm")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Company {
    private String name;
}
