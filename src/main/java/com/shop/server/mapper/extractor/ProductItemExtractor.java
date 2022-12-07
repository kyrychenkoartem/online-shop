package com.shop.server.mapper.extractor;

import com.shop.server.dao.ProductDao;
import com.shop.server.exception.ConnectionException;
import com.shop.server.model.entity.ProductItem;
import com.shop.server.model.type.ErrorResponseStatusType;
import java.sql.ResultSet;
import java.sql.SQLException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductItemExtractor implements EntityExtractor<ProductItem> {

    private static final ProductItemExtractor INSTANCE = new ProductItemExtractor();
    private final ProductDao productDao = ProductDao.getInstance();
    private final ProductExtractor extractor = ProductExtractor.getExtractor();

    @Override
    public ProductItem extract(ResultSet resultSet) throws SQLException {
        return ProductItem.builder()
                .id(resultSet.getLong("id"))
                .product(productDao.findById(resultSet.getLong("product_id"),
                                resultSet.getStatement().getConnection(),
                                extractor)
                        .orElseThrow(() -> new ConnectionException(ErrorResponseStatusType.DAO_EXCEPTION)))
                .count(resultSet.getInt("count"))
                .build();
    }

    public static ProductItemExtractor getExtractor() {
        return INSTANCE;
    }
}
