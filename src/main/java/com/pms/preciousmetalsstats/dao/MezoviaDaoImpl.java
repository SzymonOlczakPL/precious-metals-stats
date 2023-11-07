package com.pms.preciousmetalsstats.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pms.preciousmetalsstats.adapter.Adapter;
import com.pms.preciousmetalsstats.adapter.ProductType;
import com.pms.preciousmetalsstats.exceptions.HtmlProcessingOperationException;
import com.pms.preciousmetalsstats.model.PreciousMetalsDTO;
import com.pms.preciousmetalsstats.model.mezovia.external.MezoviaProduct;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.pms.preciousmetalsstats.adapter.ProductType.GOLD_COIN;
import static com.pms.preciousmetalsstats.adapter.ProductType.SILVER_COIN;
import static com.pms.preciousmetalsstats.model.CacheNames.MEZOVIA_GOLD_COINS;
import static com.pms.preciousmetalsstats.model.CacheNames.MEZOVIA_SILVER_COINS;
import static com.pms.preciousmetalsstats.model.mezovia.MezoviaHTMLTags.PRODUCT_ID_TAG;
import static com.pms.preciousmetalsstats.model.mezovia.MezoviaHTMLTags.PRODUCT_TAG;
import static com.pms.preciousmetalsstats.model.mezovia.MezoviaResources.*;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MezoviaDaoImpl implements MezoviaDao {


    private final Adapter<MezoviaProduct> mezoviaProductAdapter;
    private final Map<ProductType, String> urlMappingByProductType = Map.of(
            GOLD_COIN, GOLD_COINS_URL,
            SILVER_COIN, SILVER_COIN_URL
    );

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final RestTemplate restTemplate = new RestTemplate();

    @Cacheable(value = MEZOVIA_GOLD_COINS)
    @Override
    public List<PreciousMetalsDTO> getGoldCoinsProducts() {
        return getProductsByType(GOLD_COIN);
    }

    @Cacheable(value = MEZOVIA_SILVER_COINS)
    @Override
    public List<PreciousMetalsDTO> getSilverCoinsProducts() {
        return getProductsByType(SILVER_COIN);
    }

    private List<PreciousMetalsDTO> getProductsByType(ProductType productType) {
        String productsUrl = Optional.ofNullable(urlMappingByProductType.get(productType))
                .orElseThrow(() -> new UnsupportedOperationException("operation not supported" + productType.name()));
        try {
            Document doc = Jsoup.connect(productsUrl).get();
            return doc.getElementsByClass(PRODUCT_TAG.getTag()).stream()
                    .map(pw -> pw.attr(PRODUCT_ID_TAG.getTag()))
                    .map(productId -> {
                        String urlForDetails = String.format(PRODUCT_DETAILS_BY_ID_URL, productId);
                        String productDetail = restTemplate.getForObject(urlForDetails, String.class);
                        try {
                            return objectMapper.readValue(productDetail, MezoviaProduct.class);
                        } catch (JsonProcessingException e) {
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .map(mezoviaProductAdapter::adapt)
                    .collect(Collectors.toList());
        } catch (Exception ex) {
            throw new HtmlProcessingOperationException(ex.getMessage(), ex.getCause());
        }
    }
}
