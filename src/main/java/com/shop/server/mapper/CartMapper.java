package com.shop.server.mapper;

import com.shop.server.model.dto.cart.CartResponse;
import com.shop.server.model.dto.product_item.ProductItemResponse;
import com.shop.server.model.dto.promo_code.PromoCodeResponse;
import com.shop.server.model.entity.Cart;
import com.shop.server.model.entity.ProductItem;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static java.util.stream.Collectors.toList;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CartMapper {

    private static final CartMapper INSTANCE = new CartMapper();
    private final ProductItemMapper productItemMapper = ProductItemMapper.getInstance();
    private final PromoCodeMapper promoCodeMapper = PromoCodeMapper.getInstance();
    private static final Integer ONE_HUNDRED = 100;

    public CartResponse toResponse(List<ProductItemResponse> itemResponseList, PromoCodeResponse codeResponse) {
        if (ObjectUtils.isEmpty(itemResponseList)) {
            return null;
        }
        BigDecimal price = getPrice(itemResponseList, codeResponse);
        return CartResponse.builder()
                .productItem(itemResponseList)
                .price(price)
                .promoCode(codeResponse)
                .build();
    }

    public CartResponse toResponse(List<ProductItemResponse> itemResponseList) {
        if (ObjectUtils.isEmpty(itemResponseList)) {
            return null;
        }
        BigDecimal price = getPrice(itemResponseList, null);
        return CartResponse.builder()
                .productItem(itemResponseList)
                .price(price)
                .promoCode(null)
                .build();
    }

    public CartResponse toResponse(Cart cart) {
        if (ObjectUtils.isEmpty(cart)) {
            return null;
        }
        List<ProductItemResponse> itemResponseList = cart.getProductItems().stream()
                .map(productItemMapper::toResponse)
                .collect(toList());
        return CartResponse.builder()
                .productItem(itemResponseList)
                .price(cart.getPrice())
                .promoCode(promoCodeMapper.toResponse(cart.getPromoCode()))
                .build();
    }

    public Cart toEntity(ProductItem productItem) {
        if (ObjectUtils.isEmpty(productItem)) {
            return null;
        }
        ArrayList<ProductItem> productItems = new ArrayList<>();
        productItems.add(productItem);
        BigDecimal price = getPrice(productItem);
        return Cart.builder()
                .productItems(productItems)
                .price(price)
                .build();
    }

    public static CartMapper getInstance() {
        return INSTANCE;
    }

    private BigDecimal getPrice(List<ProductItemResponse> itemResponseList, PromoCodeResponse codeResponse) {
        HashMap<BigDecimal, Integer> map = new HashMap<>();
        for (ProductItemResponse itemResponse : itemResponseList) {
            map.put(itemResponse.getProductResponse().getPrice(), itemResponse.getCount());
        }
        double value = 0.0;
        for (Map.Entry<BigDecimal, Integer> bigDecimalIntegerEntry : map.entrySet()) {
            value += bigDecimalIntegerEntry.getKey().doubleValue() * bigDecimalIntegerEntry.getValue();
        }
        if (ObjectUtils.isNotEmpty(codeResponse)) {
            value *= ONE_HUNDRED - codeResponse.getValue();
        }
        return BigDecimal.valueOf(value);
    }

    private BigDecimal getPrice(ProductItem productItem) {
        double value = 0.0;
        value += productItem.getProduct().getPrice().doubleValue() * productItem.getCount();
        return BigDecimal.valueOf(value);
    }
}
