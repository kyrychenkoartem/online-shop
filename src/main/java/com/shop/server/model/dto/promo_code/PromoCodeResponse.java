package com.shop.server.model.dto.promo_code;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString
public class PromoCodeResponse {

    private String key;
    private Integer value;
}
