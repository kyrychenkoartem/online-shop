package com.shop.server.mapper;

import com.shop.server.exception.NotFoundException;
import com.shop.server.model.dto.product.ProductResponse;
import com.shop.server.model.dto.product_item.ProductItemResponse;
import com.shop.server.model.entity.Product;
import com.shop.server.model.entity.ProductItem;
import com.shop.server.model.type.ErrorResponseStatusType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductItemMapper {

    private static final ProductItemMapper INSTANCE = new ProductItemMapper();
    private final ProductMapper productMapper = ProductMapper.getInstance();

    public ProductItemResponse toResponse(ProductResponse product, Integer quantities) {
        if (ObjectUtils.isEmpty(product) && ObjectUtils.isEmpty(quantities)) {
            throw new NotFoundException(ErrorResponseStatusType.ARGUMENTS_NOT_FOUND_EXCEPTION);
        }
        return ProductItemResponse.builder()
                .productResponse(product)
                .count(quantities)
                .build();
    }

    public ProductItemResponse toResponse(ProductItem productItem) {
        if (ObjectUtils.isEmpty(productItem)) {
            throw new NotFoundException(ErrorResponseStatusType.ARGUMENT_NOT_FOUND_EXCEPTION, productItem);
        }
        return ProductItemResponse.builder()
                .productResponse(productMapper.toResponse(productItem.getProduct()))
                .count(productItem.getCount())
                .build();
    }

    public ProductItem toEntity(Product product, Integer quantities) {
        if (ObjectUtils.isEmpty(product) && ObjectUtils.isEmpty(quantities)) {
            throw new NotFoundException(ErrorResponseStatusType.ARGUMENTS_NOT_FOUND_EXCEPTION);
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
