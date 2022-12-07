package com.shop.server.mapper.extractor;

import com.shop.server.model.entity.Product;
import com.shop.server.model.type.Category;
import com.shop.server.model.type.Material;
import java.sql.ResultSet;
import java.sql.SQLException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductExtractor implements EntityExtractor<Product> {
    private static final ProductExtractor INSTANCE = new ProductExtractor();

    @Override
    public Product extract(ResultSet resultSet) throws SQLException {
        return Product.builder()
                .id(resultSet.getLong("id"))
                .name(resultSet.getString("name"))
                .description(resultSet.getString("description"))
                .price(resultSet.getBigDecimal("price"))
                .quantities(resultSet.getInt("quantities"))
                .category(Category.valueOf(resultSet.getString("category")))
                .material(Material.valueOf(resultSet.getString("material")))
                .build();
    }

    public static ProductExtractor getExtractor() {
        return INSTANCE;
    }
}
