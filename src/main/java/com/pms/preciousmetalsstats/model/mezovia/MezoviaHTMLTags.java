package com.pms.preciousmetalsstats.model.mezovia;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MezoviaHTMLTags {
    PRODUCT_TAG("product col-6 col-sm-4 col-md-3 pt-3 pb-md-3 mb-3 mb-sm-0"),
    PRODUCT_ID_TAG("data-product_id");

    private final String tag;
}
