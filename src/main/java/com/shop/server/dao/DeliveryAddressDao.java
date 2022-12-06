package com.shop.server.dao;

import com.shop.server.exception.DaoException;
import com.shop.server.model.entity.DeliveryAddress;
import com.shop.server.model.type.ErrorResponseStatusType;
import com.shop.server.utils.ConnectionPool;
import com.shop.server.utils.sql.DeliveryAddressSql;
import com.shop.server.utils.sql.PaymentSql;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DeliveryAddressDao implements Dao<Long, DeliveryAddress> {

    private static final DeliveryAddressDao INSTANCE = new DeliveryAddressDao();
    private final OrderDao orderDao = OrderDao.getInstance();

    @Override
    public DeliveryAddress save(DeliveryAddress address) {
        try (var connection = ConnectionPool.get();
             var preparedStatement = connection.prepareStatement(DeliveryAddressSql.SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, address.getOrder().getId());
            preparedStatement.setString(2, address.getAddress());
            preparedStatement.setString(3, address.getCity());
            preparedStatement.setString(4, address.getProvince());
            preparedStatement.setString(5, address.getPostalCode());
            preparedStatement.executeUpdate();
            var generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                address.setId(generatedKeys.getLong("id"));
            }
            return address;
        } catch (SQLException e) {
            throw new DaoException(ErrorResponseStatusType.DAO_EXCEPTION, e);
        }
    }

    @Override
    public void update(DeliveryAddress address) {
        try (var connection = ConnectionPool.get();
             var preparedStatement = connection.prepareStatement(DeliveryAddressSql.UPDATE_SQL)) {
            preparedStatement.setString(2, address.getAddress());
            preparedStatement.setString(3, address.getCity());
            preparedStatement.setString(4, address.getProvince());
            preparedStatement.setString(5, address.getPostalCode());
            preparedStatement.setLong(3, address.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(ErrorResponseStatusType.DAO_EXCEPTION, e);
        }
    }

    @Override
    public List<DeliveryAddress> findAll() {
        try (var connection = ConnectionPool.get();
             var preparedStatement = connection.prepareStatement(PaymentSql.FIND_ALL_SQL)) {
            var resultSet = preparedStatement.executeQuery();
            List<DeliveryAddress> deliveryAddresses = new ArrayList<>();
            while (resultSet.next()) {
                deliveryAddresses.add(buildAddress(resultSet));
            }
            return deliveryAddresses;
        } catch (SQLException e) {
            throw new DaoException(ErrorResponseStatusType.DAO_EXCEPTION, e);
        }
    }

    @Override
    public Optional<DeliveryAddress> findById(Long id) {
        try (var connection = ConnectionPool.get()) {
            return findById(id, connection);
        } catch (SQLException e) {
            throw new DaoException(ErrorResponseStatusType.DAO_EXCEPTION, e);
        }
    }

    public Optional<DeliveryAddress> findById(Long id, Connection connection) {
        try (var preparedStatement = connection.prepareStatement(DeliveryAddressSql.FIND_BY_ID_SQL)) {
            preparedStatement.setLong(1, id);
            var resultSet = preparedStatement.executeQuery();
            DeliveryAddress deliveryAddress = null;
            if (resultSet.next()) {
                deliveryAddress = buildAddress(resultSet);
            }
            return Optional.ofNullable(deliveryAddress);
        } catch (SQLException e) {
            throw new DaoException(ErrorResponseStatusType.DAO_EXCEPTION, e);
        }
    }

    @Override
    public boolean delete(Long id) {
        try (var connection = ConnectionPool.get();
             var preparedStatement = connection.prepareStatement(DeliveryAddressSql.DELETE_SQL)) {
            preparedStatement.setLong(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(ErrorResponseStatusType.DAO_EXCEPTION, e);
        }
    }

    public static DeliveryAddressDao getInstance() {
        return INSTANCE;
    }

    private DeliveryAddress buildAddress(ResultSet resultSet) throws SQLException {
        return DeliveryAddress.builder()
                .id(resultSet.getLong("id"))
                .order(orderDao.findById(resultSet.getLong("orders_id"),
                                resultSet.getStatement().getConnection())
                        .orElseThrow(() -> new DaoException(ErrorResponseStatusType.DAO_EXCEPTION)))
                .address(resultSet.getString("address"))
                .city(resultSet.getString("city"))
                .province(resultSet.getString("province"))
                .postalCode(resultSet.getString("postal_code"))
                .build();
    }
}
