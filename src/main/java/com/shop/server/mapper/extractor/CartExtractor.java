package com.shop.server.mapper.extractor;

import com.shop.server.dao.ProductItemDao;
import com.shop.server.dao.PromoCodeDao;
import com.shop.server.exception.ConnectionException;
import com.shop.server.model.entity.Cart;
import com.shop.server.model.type.ErrorResponseStatusType;
import java.sql.ResultSet;
import java.sql.SQLException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CartExtractor implements EntityExtractor<Cart> {

    private static final CartExtractor INSTANCE = new CartExtractor();

    private final ProductItemDao productItemDao = ProductItemDao.getInstance();
    private final PromoCodeDao promoCodeDao = PromoCodeDao.getInstance();
    private final ProductItemExtractor itemExtractor = ProductItemExtractor.getExtractor();
    private final PromoCodeExtractor promoCodeExtractor = PromoCodeExtractor.getExtractor();

    @Override
    public Cart extract(ResultSet resultSet) throws SQLException {
        return Cart.builder()
                .id(resultSet.getLong("id"))
                .productItem(productItemDao.findById(resultSet.getLong("product_item_id"),
                                resultSet.getStatement().getConnection(),
                                itemExtractor)
                        .orElseThrow(() -> new ConnectionException(ErrorResponseStatusType.DAO_EXCEPTION)))
                .price(resultSet.getBigDecimal("price"))
                .promoCode(promoCodeDao.findById(resultSet.getLong("promo_code_id"),
                                resultSet.getStatement().getConnection(),
                                promoCodeExtractor)
                        .orElseThrow(() -> new ConnectionException(ErrorResponseStatusType.DAO_EXCEPTION)))
                .build();
    }

    public static CartExtractor getExtractor() {
        return INSTANCE;
    }
}
