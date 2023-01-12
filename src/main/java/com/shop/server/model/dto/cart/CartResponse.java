package com.shop.server.model.dto.cart;

import com.shop.server.model.dto.product_item.ProductItemResponse;
import com.shop.server.model.dto.promo_code.PromoCodeResponse;
import java.math.BigDecimal;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class CartResponse {

    private Long id;
    private List<ProductItemResponse> productItem;
    private BigDecimal price;
    private PromoCodeResponse promoCode;
}
