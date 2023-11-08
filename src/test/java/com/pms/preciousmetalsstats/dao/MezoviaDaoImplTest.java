package com.pms.preciousmetalsstats.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pms.preciousmetalsstats.adapter.Adapter;
import com.pms.preciousmetalsstats.exceptions.HtmlProcessingOperationException;
import com.pms.preciousmetalsstats.exceptions.JsonProcessingOperationException;
import com.pms.preciousmetalsstats.model.PreciousMetalsDTO;
import com.pms.preciousmetalsstats.model.mezovia.external.MezoviaProduct;
import lombok.SneakyThrows;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

import static com.pms.preciousmetalsstats.model.mezovia.MezoviaHTMLTags.PRODUCT_ID_TAG;
import static com.pms.preciousmetalsstats.model.mezovia.MezoviaHTMLTags.PRODUCT_TAG;
import static com.pms.preciousmetalsstats.model.mezovia.MezoviaResources.GOLD_COINS_URL;
import static com.pms.preciousmetalsstats.model.mezovia.MezoviaResources.SILVER_COIN_URL;
import static org.jsoup.helper.HttpConnection.DEFAULT_UA;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class MezoviaDaoImplTest {

    private final ObjectMapper objectMapper = mock(ObjectMapper.class);
    private final RestTemplate restTemplate = mock(RestTemplate.class);
    private final Adapter<MezoviaProduct> mezoviaProductAdapter = mock(Adapter.class);
    private final MezoviaDaoImpl mezoviaDao = new MezoviaDaoImpl(objectMapper, restTemplate, mezoviaProductAdapter);
    private final Connection connection = mock(Connection.class);
    private final Document document = mock(Document.class);
    private final Elements elements = mock(Elements.class);
    private final Element element = mock(Element.class);
    private final String productId = "1";
    private final String productDetails = "productDetails";

    @SneakyThrows
    @Test
    void getGoldCoinsProductsTest() {
        //GIVEN
        try (MockedStatic<Jsoup> jsoupMock = mockStatic(Jsoup.class)) {
            jsoupMock.when(() -> Jsoup.connect(GOLD_COINS_URL)).thenReturn(connection);
            when(connection.userAgent(DEFAULT_UA)).thenReturn(connection);
            when(connection.get()).thenReturn(document);
            when(document.getElementsByClass(PRODUCT_TAG.getTag())).thenReturn(elements);
            when(elements.stream()).thenReturn(Stream.of(element));
            when(element.attr(PRODUCT_ID_TAG.getTag())).thenReturn(productId);
            when(restTemplate.getForObject("https://mennicamazovia.pl/ajax/projector.php?action=get&product=1&get=sizes", String.class)).thenReturn(productDetails);

            MezoviaProduct mezoviaProduct = new MezoviaProduct();
            PreciousMetalsDTO expectedPreciousMetalsDTO = PreciousMetalsDTO.builder().build();
            when(objectMapper.readValue(productDetails, MezoviaProduct.class)).thenReturn(mezoviaProduct);
            when(mezoviaProductAdapter.adapt(mezoviaProduct)).thenReturn(expectedPreciousMetalsDTO);

            //WHEN
            List<PreciousMetalsDTO> metalsDTOS = mezoviaDao.getGoldCoinsProducts();

            //THEN
            assertEquals(List.of(expectedPreciousMetalsDTO), metalsDTOS);
        }
    }

    @SneakyThrows
    @Test
    void getSilverCoinsProductsTest() {
        //GIVEN
        try (MockedStatic<Jsoup> jsoupMock = mockStatic(Jsoup.class)) {
            jsoupMock.when(() -> Jsoup.connect(SILVER_COIN_URL)).thenReturn(connection);
            when(connection.userAgent(DEFAULT_UA)).thenReturn(connection);
            when(connection.get()).thenReturn(document);
            when(document.getElementsByClass(PRODUCT_TAG.getTag())).thenReturn(elements);
            when(elements.stream()).thenReturn(Stream.of(element));
            when(element.attr(PRODUCT_ID_TAG.getTag())).thenReturn(productId);
            when(restTemplate.getForObject("https://mennicamazovia.pl/ajax/projector.php?action=get&product=1&get=sizes", String.class)).thenReturn(productDetails);

            MezoviaProduct mezoviaProduct = new MezoviaProduct();
            PreciousMetalsDTO expectedPreciousMetalsDTO = PreciousMetalsDTO.builder().build();
            when(objectMapper.readValue(productDetails, MezoviaProduct.class)).thenReturn(mezoviaProduct);
            when(mezoviaProductAdapter.adapt(mezoviaProduct)).thenReturn(expectedPreciousMetalsDTO);

            //WHEN
            List<PreciousMetalsDTO> metalsDTOS = mezoviaDao.getSilverCoinsProducts();

            //THEN
            assertEquals(List.of(expectedPreciousMetalsDTO), metalsDTOS);
        }
    }

    @SneakyThrows
    @Test
    void getGoldCoinsProductsTest_htmlProcessingOperationException() {
        //GIVEN
        try (MockedStatic<Jsoup> jsoupMock = mockStatic(Jsoup.class)) {
            jsoupMock.when(() -> Jsoup.connect(GOLD_COINS_URL)).thenReturn(connection);
            when(connection.userAgent(DEFAULT_UA)).thenReturn(connection);
            when(connection.get()).thenThrow(new IOException("exception"));

            //THEN
            assertThrows(HtmlProcessingOperationException.class, mezoviaDao::getGoldCoinsProducts);
        }
    }

    @SneakyThrows
    @Test
    void getSilverCoinsProductsTest_htmlProcessingOperationException() {
        //GIVEN
        try (MockedStatic<Jsoup> jsoupMock = mockStatic(Jsoup.class)) {
            jsoupMock.when(() -> Jsoup.connect(SILVER_COIN_URL)).thenReturn(connection);
            when(connection.userAgent(DEFAULT_UA)).thenReturn(connection);
            when(connection.get()).thenThrow(new IOException("exception"));

            //THEN
            assertThrows(HtmlProcessingOperationException.class, mezoviaDao::getSilverCoinsProducts);
        }
    }
}