package com.shop.server.mapper.extractor;

import com.shop.server.dao.OrderDao;
import com.shop.server.exception.DaoException;
import com.shop.server.model.entity.DeliveryAddress;
import com.shop.server.model.type.ErrorResponseStatusType;
import java.sql.ResultSet;
import java.sql.SQLException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DeliveryAddressExtractor implements EntityExtractor<DeliveryAddress> {

    private static final DeliveryAddressExtractor INSTANCE = new DeliveryAddressExtractor();
    private final OrderDao orderDao = OrderDao.getInstance();
    private final OrderExtractor extractor = OrderExtractor.getExtractor();

    @Override
    public DeliveryAddress extract(ResultSet resultSet) throws SQLException {
        return DeliveryAddress.builder()
                .id(resultSet.getLong("id"))
                .order(orderDao.findById(resultSet.getLong("orders_id"),
                                resultSet.getStatement().getConnection(),
                                extractor)
                        .orElseThrow(() -> new DaoException(ErrorResponseStatusType.DAO_EXCEPTION)))
                .address(resultSet.getString("address"))
                .city(resultSet.getString("city"))
                .province(resultSet.getString("province"))
                .postalCode(resultSet.getString("postal_code"))
                .build();
    }

    public static DeliveryAddressExtractor getExtractor() {
        return INSTANCE;
    }
}
