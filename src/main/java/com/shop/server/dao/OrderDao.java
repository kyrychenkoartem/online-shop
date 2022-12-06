package com.shop.server.dao;

import com.shop.server.exception.DaoException;
import com.shop.server.model.entity.Order;
import com.shop.server.model.type.ErrorResponseStatusType;
import com.shop.server.model.type.OrderStatus;
import com.shop.server.utils.ConnectionPool;
import com.shop.server.utils.sql.OrderSql;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderDao implements Dao<Long, Order> {

    private static final OrderDao INSTANCE = new OrderDao();
    private final CartDao cartDao = CartDao.getInstance();
    private final UserDao userDao = UserDao.getInstance();

    @Override
    public Order save(Order order) {
        try (var connection = ConnectionPool.get();
             var preparedStatement = connection.prepareStatement(OrderSql.SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, order.getCart().getId());
            preparedStatement.setLong(2, order.getUser().getId());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(order.getClosedAt()));
            preparedStatement.setString(4, order.getStatus().name());
            preparedStatement.executeUpdate();
            var generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                order.setId(generatedKeys.getLong("id"));
            }
            return order;
        } catch (SQLException e) {
            throw new DaoException(ErrorResponseStatusType.DAO_EXCEPTION, e);
        }
    }

    @Override
    public void update(Order order) {
        try (var connection = ConnectionPool.get();
             var preparedStatement = connection.prepareStatement(OrderSql.UPDATE_SQL)) {
            preparedStatement.setTimestamp(1, Timestamp.valueOf(order.getClosedAt()));
            preparedStatement.setString(2, order.getStatus().name());
            preparedStatement.setLong(3, order.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(ErrorResponseStatusType.DAO_EXCEPTION, e);
        }
    }

    @Override
    public List<Order> findAll() {
        try (var connection = ConnectionPool.get();
             var preparedStatement = connection.prepareStatement(OrderSql.FIND_ALL_SQL)) {
            var resultSet = preparedStatement.executeQuery();
            List<Order> orders = new ArrayList<>();
            while (resultSet.next()) {
                orders.add(buildOrder(resultSet));
            }
            return orders;
        } catch (SQLException e) {
            throw new DaoException(ErrorResponseStatusType.DAO_EXCEPTION, e);
        }
    }

    @Override
    public Optional<Order> findById(Long id) {
        try (var connection = ConnectionPool.get()) {
            return findById(id, connection);
        } catch (SQLException e) {
            throw new DaoException(ErrorResponseStatusType.DAO_EXCEPTION, e);
        }
    }

    public Optional<Order> findById(Long id, Connection connection) {
        try (var preparedStatement = connection.prepareStatement(OrderSql.FIND_BY_ID_SQL)) {
            preparedStatement.setLong(1, id);
            var resultSet = preparedStatement.executeQuery();
            Order order = null;
            if (resultSet.next()) {
                order = buildOrder(resultSet);
            }
            return Optional.ofNullable(order);
        } catch (SQLException e) {
            throw new DaoException(ErrorResponseStatusType.DAO_EXCEPTION, e);
        }
    }

    @Override
    public boolean delete(Long id) {
        try (var connection = ConnectionPool.get();
             var preparedStatement = connection.prepareStatement(OrderSql.DELETE_SQL)) {
            preparedStatement.setLong(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(ErrorResponseStatusType.DAO_EXCEPTION, e);
        }
    }

    public static OrderDao getInstance() {
        return INSTANCE;
    }

    private Order buildOrder(ResultSet resultSet) throws SQLException {
        return Order.builder()
                .id(resultSet.getLong("id"))
                .cart(cartDao.findById(resultSet.getLong("cart_id"),
                                resultSet.getStatement().getConnection())
                        .orElseThrow(() -> new DaoException(ErrorResponseStatusType.DAO_EXCEPTION)))
                .user(userDao.findById(resultSet.getLong("users_id"),
                                resultSet.getStatement().getConnection())
                        .orElseThrow(() -> new DaoException(ErrorResponseStatusType.DAO_EXCEPTION)))
                .createdAt(resultSet.getTimestamp("created_at").toLocalDateTime())
                .status(OrderStatus.valueOf(resultSet.getString("status")))
                .build();
    }
}
