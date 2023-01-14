package com.shop.server.model.dto.product;

import com.shop.server.model.type.Category;
import com.shop.server.model.type.Material;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class UpdateProductRequest {

    private String name;
    private String description;
    private BigDecimal price;
    private Integer quantities;
    private Category category;
    private Material material;
}
