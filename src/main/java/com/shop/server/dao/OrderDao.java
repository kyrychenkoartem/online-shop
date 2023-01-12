package com.shop.server.dao;

import com.shop.server.exception.DaoException;
import com.shop.server.mapper.extractor.EntityExtractor;
import com.shop.server.model.entity.Order;
import com.shop.server.model.type.ErrorResponseStatusType;
import com.shop.server.utils.ConnectionPool;
import com.shop.server.utils.sql.OrderSql;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderDao implements Dao<Long, Order> {

    private static final OrderDao INSTANCE = new OrderDao();

    @Override
    public Order save(Order order) {
        try (var connection = ConnectionPool.get();
             var preparedStatement = connection.prepareStatement(OrderSql.SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, order.getCart().getId());
            preparedStatement.setLong(2, order.getUser().getId());
            preparedStatement.setString(3, order.getStatus().name());
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
        try (var connection = ConnectionPool.get()) {
            update(order, connection);
        } catch (SQLException e) {
            throw new DaoException(ErrorResponseStatusType.DAO_EXCEPTION, e);
        }
    }

    public void update(Order order, Connection connection) {
        try (var preparedStatement = connection.prepareStatement(OrderSql.UPDATE_SQL)) {
            preparedStatement.setString(1, order.getStatus().name());
            preparedStatement.setLong(2, order.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(ErrorResponseStatusType.DAO_EXCEPTION, e);
        }
    }

    @Override
    public List<Order> findAll(EntityExtractor<Order> extractor) {
        try (var connection = ConnectionPool.get();
             var preparedStatement = connection.prepareStatement(OrderSql.FIND_ALL_SQL)) {
            var resultSet = preparedStatement.executeQuery();
            List<Order> orders = new ArrayList<>();
            while (resultSet.next()) {
                orders.add(extractor.extract(resultSet));
            }
            return orders;
        } catch (SQLException e) {
            throw new DaoException(ErrorResponseStatusType.DAO_EXCEPTION, e);
        }
    }

    @Override
    public Optional<Order> findById(Long id, EntityExtractor<Order> extractor) {
        try (var connection = ConnectionPool.get()) {
            return findById(id, connection, extractor);
        } catch (SQLException e) {
            throw new DaoException(ErrorResponseStatusType.DAO_EXCEPTION, e);
        }
    }

    public Optional<Order> findById(Long id, Connection connection, EntityExtractor<Order> extractor) {
        try (var preparedStatement = connection.prepareStatement(OrderSql.FIND_BY_ID_SQL)) {
            preparedStatement.setLong(1, id);
            var resultSet = preparedStatement.executeQuery();
            Order order = null;
            if (resultSet.next()) {
                order = extractor.extract(resultSet);
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

}
