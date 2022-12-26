package com.shop.server.mapper;

import com.shop.server.model.dto.product.ProductResponse;
import com.shop.server.model.dto.product_item.ProductItemResponse;
import com.shop.server.model.entity.Product;
import com.shop.server.model.entity.ProductItem;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductItemMapper {

    private static final ProductItemMapper INSTANCE = new ProductItemMapper();
    private final ProductMapper productMapper = ProductMapper.getInstance();

    public ProductItemResponse toResponse(ProductResponse product, Integer quantities) {
        if (ObjectUtils.isEmpty(product) && ObjectUtils.isEmpty(quantities)) {
            return null;
        }
        return ProductItemResponse.builder()
                .productResponse(product)
                .count(quantities)
                .build();
    }

    public ProductItemResponse toResponse(ProductItem productItem) {
        if (ObjectUtils.isEmpty(productItem)) {
            return null;
        }
        return ProductItemResponse.builder()
                .productResponse(productMapper.toResponse(productItem.getProduct()))
                .count(productItem.getCount())
                .build();
    }

    public ProductItem toEntity(Product product, Integer quantities) {
        if (ObjectUtils.isEmpty(product) && ObjectUtils.isEmpty(quantities)) {
            return null;
        }
        return ProductItem.builder()
                .product(product)
                .count(quantities)
                .build();
    }

    public static ProductItemMapper getInstance() {
        return INSTANCE;
    }
}
