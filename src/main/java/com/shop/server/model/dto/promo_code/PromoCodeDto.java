package com.shop.server.model.dto.promo_code;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class PromoCodeDto {

    private String key;
    private Integer value;
}
