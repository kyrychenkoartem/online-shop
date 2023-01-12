package com.shop.server.mapper;

import com.shop.server.exception.NotFoundException;
import com.shop.server.model.dto.cart.CartResponse;
import com.shop.server.model.dto.product_item.ProductItemResponse;
import com.shop.server.model.dto.promo_code.PromoCodeResponse;
import com.shop.server.model.entity.Cart;
import com.shop.server.model.entity.ProductItem;
import com.shop.server.model.entity.PromoCode;
import com.shop.server.model.type.ErrorResponseStatusType;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;

import static java.util.stream.Collectors.toList;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CartMapper {

    private static final CartMapper INSTANCE = new CartMapper();
    private final ProductItemMapper productItemMapper = ProductItemMapper.getInstance();
    private final PromoCodeMapper promoCodeMapper = PromoCodeMapper.getInstance();
    private static final Integer ONE_HUNDRED = 100;

    public CartResponse toResponse(List<ProductItemResponse> itemResponseList, PromoCodeResponse codeResponse) {
        if (ObjectUtils.isEmpty(itemResponseList)) {
            throw new NotFoundException(ErrorResponseStatusType.ARGUMENT_NOT_FOUND_EXCEPTION, itemResponseList);
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
            throw new NotFoundException(ErrorResponseStatusType.ARGUMENT_NOT_FOUND_EXCEPTION, itemResponseList);
        }
        BigDecimal price = getPrice(itemResponseList);
        return CartResponse.builder()
                .productItem(itemResponseList)
                .price(price)
                .build();
    }

    public CartResponse toResponse(Cart cart) {
        if (ObjectUtils.isEmpty(cart)) {
            throw new NotFoundException(ErrorResponseStatusType.ARGUMENT_NOT_FOUND_EXCEPTION, cart);
        }
        List<ProductItemResponse> itemResponseList = cart.getProductItems().stream()
                .map(productItemMapper::toResponse)
                .collect(toList());
        var optionalPromoCode = Optional.ofNullable(cart.getPromoCode());
        if (optionalPromoCode.isPresent()) {
            var promoCode = optionalPromoCode.get();
            var promoCodeResponse = promoCodeMapper.toResponse(promoCode);
            return CartResponse.builder()
                    .id(cart.getId())
                    .productItem(itemResponseList)
                    .price(cart.getPrice())
                    .promoCode(promoCodeResponse)
                    .build();
        } else {
            return CartResponse.builder()
                    .id(cart.getId())
                    .productItem(itemResponseList)
                    .price(cart.getPrice())
                    .build();
        }
    }

    public Cart toEntity(ProductItem productItem) {
        if (ObjectUtils.isEmpty(productItem)) {
            throw new NotFoundException(ErrorResponseStatusType.ARGUMENT_NOT_FOUND_EXCEPTION, productItem);
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

    private BigDecimal getPrice(List<ProductItemResponse> itemResponseList) {
        HashMap<BigDecimal, Integer> map = new HashMap<>();
        for (ProductItemResponse itemResponse : itemResponseList) {
            map.put(itemResponse.getProductResponse().getPrice(), itemResponse.getCount());
        }
        double value = 0.0;
        for (Map.Entry<BigDecimal, Integer> bigDecimalIntegerEntry : map.entrySet()) {
            value += bigDecimalIntegerEntry.getKey().doubleValue() * bigDecimalIntegerEntry.getValue();
        }
        return BigDecimal.valueOf(value);
    }

    public BigDecimal getPrice(ProductItem productItem) {
        double value = 0.0;
        value += productItem.getProduct().getPrice().doubleValue() * productItem.getCount();
        return BigDecimal.valueOf(value);
    }

    public BigDecimal getPrice(BigDecimal price, PromoCode promoCode) {
        double value = price.doubleValue();
        double percent;
        if (ObjectUtils.isNotEmpty(promoCode) && !Objects.equals(promoCode.getKey(), "default")) {
            percent = ((double) (ONE_HUNDRED - promoCode.getValue()) / (ONE_HUNDRED));
            value *= percent;
        }
        return BigDecimal.valueOf(value);
    }
}
