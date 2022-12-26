package com.shop.server.model.dto.product;

import com.shop.server.model.type.Category;
import com.shop.server.model.type.Material;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString
public class ProductFilter {
    private int limit;
    private int offset;
    private String name;
    private BigDecimal price;
    private Category category;
    private Material material;
}
