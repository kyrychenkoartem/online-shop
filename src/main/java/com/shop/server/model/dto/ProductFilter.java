package com.shop.server.model.dto;

import com.shop.server.model.type.Category;
import com.shop.server.model.type.Material;
import java.math.BigDecimal;

public record ProductFilter(int limit,
                            int offset,
                            BigDecimal price,
                            Category category,
                            Material material) {
}
