package com.shop.server.mapper;

import com.shop.server.model.dto.product.NewProductRequest;
import com.shop.server.model.dto.product.ProductResponse;
import com.shop.server.model.dto.product.UpdateProductPriceRequest;
import com.shop.server.model.dto.product.UpdateProductQuantitiesRequest;
import com.shop.server.model.dto.product.UpdateProductRequest;
import com.shop.server.model.entity.Product;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductMapper {

    private static final ProductMapper INSTANCE = new ProductMapper();

    public static ProductMapper getInstance() {
        return INSTANCE;
    }

    public ProductResponse toResponse(Product product) {
        if (ObjectUtils.isEmpty(product)) {
            return null;
        }
        return ProductResponse.builder()
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .category(product.getCategory())
                .material(product.getMaterial())
                .build();
    }

    public Product toEntity(NewProductRequest request) {
        if (ObjectUtils.isEmpty(request)) {
            return null;
        }
        return Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .quantities(request.getQuantities())
                .category(request.getCategory())
                .material(request.getMaterial())
                .build();
    }

    public Product toEntity(UpdateProductRequest request, Product product) {
        if (ObjectUtils.isEmpty(request)) {
            return null;
        }
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setQuantities(request.getQuantities());
        product.setCategory(request.getCategory());
        product.setMaterial(request.getMaterial());
        return product;
    }

    public Product toEntity(UpdateProductPriceRequest request, Product product) {
        if (ObjectUtils.isEmpty(request)) {
            return null;
        }
        product.setPrice(request.getPrice());
        return product;
    }

    public Product toEntity(UpdateProductQuantitiesRequest request, Product product) {
        if (ObjectUtils.isEmpty(request)) {
            return null;
        }
        product.setQuantities(request.getQuantities());
        return product;
    }

    public Product toEntity(ProductResponse response) {
        if (ObjectUtils.isEmpty(response)) {
            return null;
        }
        return Product.builder()

                .build();
    }
}
