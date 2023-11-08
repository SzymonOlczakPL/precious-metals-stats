package com.pms.preciousmetalsstats.adapter.mezovia;

import com.pms.preciousmetalsstats.adapter.Adapter;
import com.pms.preciousmetalsstats.model.PreciousMetalsDTO;
import com.pms.preciousmetalsstats.model.mezovia.external.Company;
import com.pms.preciousmetalsstats.model.mezovia.external.MezoviaProduct;
import com.pms.preciousmetalsstats.model.mezovia.external.MezoviaProductDetails;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

import static java.util.Objects.nonNull;
import static java.util.Optional.ofNullable;

@Component
public class MezoviaAdapter implements Adapter<MezoviaProduct> {

    private final static Pattern ouncePattern = Pattern.compile("(\\d+\\/?\\d?+) [uncj+Aa-z]+|(\\d+\\/\\d+) [uncj+Aa-z]+");
    private final static Pattern weightInGramsPattern = Pattern.compile("(\\d+?[.]?\\d+[g])|(\\d+[g])");


    @Override
    public PreciousMetalsDTO adapt(MezoviaProduct mezoviaProduct) {
        MezoviaProductDetails mezoviaProductDetails = ofNullable(mezoviaProduct.getMezoviaProductDetails())
                .orElseThrow(() -> new RuntimeException("missing product details"));

        String companyName = ofNullable(mezoviaProductDetails.getCompany())
                .map(Company::getName)
                .orElse("undefined");

        return PreciousMetalsDTO.builder()
                .coinName(mezoviaProductDetails.getName())
                .coinCompanyName(companyName)
                .vat(mezoviaProductDetails.getTax().getVat())
                .amount(mezoviaProductDetails.getAmount())
                .retailPrice(mezoviaProductDetails.getItem().getItemDetails().getPrice().getPriceRetail())
                .netPrice(mezoviaProductDetails.getItem().getItemDetails().getPrice().getPriceNet())
                .ounceValue(getOunceValueFromCoinName(mezoviaProductDetails.getName()))
                .weight(getWeightFromCoinName(mezoviaProductDetails.getName()))
                .build();
    }

    /**
     * In Mezovia ounce value if exists is included in the coin name
     *
     * @return ounce value represent as String
     */
    private String getOunceValueFromCoinName(String coinName) {
        return ouncePattern.matcher(coinName)
                .results()
                .findFirst()
                .map(m -> nonNull(m.group(1)) ? m.group(1) : m.group(2))
                .orElse(null);
    }

    /**
     * In Mezovia weight if exists is included in the coin name
     *
     * @return weight represented as double value
     */
    private double getWeightFromCoinName(String coinName) {
        return weightInGramsPattern.matcher(coinName)
                .results()
                .findFirst()
                .map(m -> nonNull(m.group(1)) ? m.group(1) : m.group(2))
                .map(m -> m.replace("g", ""))
                .map(Double::valueOf)
                .orElse(0.0);
    }
}
