package com.pms.preciousmetalsstats.adapter.mezovia;

import com.pms.preciousmetalsstats.model.PreciousMetalsDTO;
import com.pms.preciousmetalsstats.model.mezovia.external.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MezoviaAdapterTest {

    private final MezoviaAdapter mezoviaAdapter = new MezoviaAdapter();

    @Test
    void adaptMezoviaProductTest() {
        //GIVEN
        MezoviaProductDetails mezoviaProductDetails = getMezoviaProductDetails("TEST_NAME");
        MezoviaProduct mezoviaProduct = new MezoviaProduct();
        mezoviaProduct.setMezoviaProductDetails(mezoviaProductDetails);

        PreciousMetalsDTO expectedPMDTO = PreciousMetalsDTO.builder()
                .coinName(mezoviaProductDetails.getName())
                .coinCompanyName(mezoviaProductDetails.getCompany().getName())
                .vat(mezoviaProductDetails.getTax().getVat())
                .amount(mezoviaProductDetails.getAmount())
                .retailPrice(mezoviaProductDetails.getItem().getItemDetails().getPrice().getPriceRetail())
                .netPrice(mezoviaProductDetails.getItem().getItemDetails().getPrice().getPriceNet())
                .build();

        //WHEN
        PreciousMetalsDTO actualPMDTO = mezoviaAdapter.adapt(mezoviaProduct);

        //THEN
        assertEquals(expectedPMDTO, actualPMDTO);
    }

    @ParameterizedTest
    @ValueSource(strings = {"1", "1/2", "1/10", "1/100", "1/1000"})
    void adaptMezoviaProductTest_ounceAdaptation(String ounceFormat) {
        //GIVEN
        Company company = new Company();
        company.setName("TEST_COMPANY_NAME");
        Tax tax = new Tax();
        tax.setVat(1.0);
        MezoviaProductDetails mezoviaProductDetails = getMezoviaProductDetails("TEST_NAME " + ounceFormat + " uncj");
        MezoviaProduct mezoviaProduct = new MezoviaProduct();
        mezoviaProduct.setMezoviaProductDetails(mezoviaProductDetails);

        PreciousMetalsDTO expectedPMDTO = PreciousMetalsDTO.builder()
                .ounceValue(ounceFormat)
                .build();

        //WHEN
        PreciousMetalsDTO actualPMDTO = mezoviaAdapter.adapt(mezoviaProduct);

        //THEN
        assertEquals(expectedPMDTO.getOunceValue(), actualPMDTO.getOunceValue());
    }

    @ParameterizedTest
    @ValueSource(strings = {"1g", "32g", "32.1g", "325.23g", "12544.123543g", "0.5g", "0.0005g"})
    void adaptMezoviaProductTest_weightAdaptation(String weightFormat) {
        //GIVEN
        Company company = new Company();
        company.setName("TEST_COMPANY_NAME");
        Tax tax = new Tax();
        tax.setVat(1.0);
        MezoviaProductDetails mezoviaProductDetails = getMezoviaProductDetails("TEST_NAME " + weightFormat);
        MezoviaProduct mezoviaProduct = new MezoviaProduct();
        mezoviaProduct.setMezoviaProductDetails(mezoviaProductDetails);

        Double expectedWeight = Double.parseDouble(weightFormat.replace("g", ""));

        //WHEN
        PreciousMetalsDTO actualPMDTO = mezoviaAdapter.adapt(mezoviaProduct);

        //THEN
        assertEquals(expectedWeight, actualPMDTO.getWeight());
    }

    private static MezoviaProductDetails getMezoviaProductDetails(String coinName) {
        Company company = new Company();
        company.setName("TEST_COMPANY_NAME");
        Tax tax = new Tax();
        tax.setVat(1.0);
        Price price = new Price();
        price.setPriceNet(1.5);
        price.setPricePos(1.6);
        price.setPriceRetail(1.7);
        ItemDetails itemDetails = new ItemDetails();
        itemDetails.setPrice(price);
        Item item = new Item();
        item.setItemDetails(itemDetails);
        MezoviaProductDetails mezoviaProductDetails = new MezoviaProductDetails();
        mezoviaProductDetails.setName(coinName);
        mezoviaProductDetails.setCompany(company);
        mezoviaProductDetails.setTax(tax);
        mezoviaProductDetails.setAmount(1L);
        mezoviaProductDetails.setItem(item);
        return mezoviaProductDetails;
    }
}