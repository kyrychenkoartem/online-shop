package com.shop.server.model.dto.product;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class UpdateProductPriceRequest {

    private BigDecimal price;
}
