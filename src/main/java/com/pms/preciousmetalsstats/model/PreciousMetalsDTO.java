package com.pms.preciousmetalsstats.model;

import lombok.*;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@ToString
public class PreciousMetalsDTO {
    private String coinName;
    private String coinCompanyName;
    private double vat;
    private long amount;
    private double retailPrice;
    private double netPrice;
    private double weight;
    private String ounceValue;
}
