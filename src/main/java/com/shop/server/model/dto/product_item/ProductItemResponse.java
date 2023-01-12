package com.shop.server.model.dto.product_item;


import com.shop.server.model.dto.product.ProductResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString
public class ProductItemResponse {

    private ProductResponse productResponse;
    private Integer count;
}
