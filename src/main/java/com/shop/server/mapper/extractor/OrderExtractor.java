package com.shop.server.mapper.extractor;

import com.shop.server.dao.CartDao;
import com.shop.server.dao.UserDao;
import com.shop.server.exception.DaoException;
import com.shop.server.model.entity.Order;
import com.shop.server.model.type.ErrorResponseStatusType;
import com.shop.server.model.type.OrderStatus;
import java.sql.ResultSet;
import java.sql.SQLException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderExtractor implements EntityExtractor<Order> {

    private static final OrderExtractor INSTANCE = new OrderExtractor();
    private final CartDao cartDao = CartDao.getInstance();
    private final UserDao userDao = UserDao.getInstance();
    private final CartExtractor cartExtractor = CartExtractor.getExtractor();

    private final UserExtractor userExtractor = UserExtractor.getExtractor();

    @Override
    public Order extract(ResultSet resultSet) throws SQLException {
        return Order.builder()
                .id(resultSet.getLong("id"))
                .cart(cartDao.findById(resultSet.getLong("cart_id"),
                                resultSet.getStatement().getConnection(),
                                cartExtractor)
                        .orElseThrow(() -> new DaoException(ErrorResponseStatusType.DAO_EXCEPTION)))
                .user(userDao.findById(resultSet.getLong("users_id"),
                                resultSet.getStatement().getConnection(),
                                userExtractor)
                        .orElseThrow(() -> new DaoException(ErrorResponseStatusType.DAO_EXCEPTION)))
                .createdAt(resultSet.getTimestamp("created_at").toLocalDateTime())
                .status(OrderStatus.valueOf(resultSet.getString("status")))
                .build();
    }

    public static OrderExtractor getExtractor() {
        return INSTANCE;
    }
}
