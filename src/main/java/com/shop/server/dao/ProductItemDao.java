package com.shop.server.dao;

import com.shop.server.exception.DaoException;
import com.shop.server.mapper.extractor.EntityExtractor;
import com.shop.server.model.entity.ProductItem;
import com.shop.server.model.type.ErrorResponseStatusType;
import com.shop.server.utils.ConnectionPool;
import com.shop.server.utils.sql.ProductItemSql;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductItemDao implements Dao<Long, ProductItem> {

    private static final ProductItemDao INSTANCE = new ProductItemDao();

    @Override
    public ProductItem save(ProductItem productItem) {
        try (var connection = ConnectionPool.get()) {
            return save(productItem, connection);
        } catch (SQLException e) {
            throw new DaoException(ErrorResponseStatusType.DAO_EXCEPTION, e);
        }
    }

    public ProductItem save(ProductItem productItem, Connection connection) {
        try (var preparedStatement = connection.prepareStatement(ProductItemSql.SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, productItem.getProduct().getId());
            preparedStatement.setInt(2, productItem.getCount());
            preparedStatement.executeUpdate();
            var generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                productItem.setId(generatedKeys.getLong("id"));
            }
            return productItem;
        } catch (SQLException e) {
            throw new DaoException(ErrorResponseStatusType.DAO_EXCEPTION, e);
        }
    }

    public ProductItem save(ProductItem productItem, Long cartId, Connection connection) {
        try (var preparedStatement = connection.prepareStatement(ProductItemSql.SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, productItem.getProduct().getId());
            preparedStatement.setInt(2, productItem.getCount());
            preparedStatement.setLong(3, cartId);
            preparedStatement.executeUpdate();
            var generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                productItem.setId(generatedKeys.getLong("id"));
            }
            return productItem;
        } catch (SQLException e) {
            throw new DaoException(ErrorResponseStatusType.DAO_EXCEPTION, e);
        }
    }

    @Override
    public void update(ProductItem productItem) {
        try (var connection = ConnectionPool.get()) {
            update(productItem, connection);
        } catch (SQLException e) {
            throw new DaoException(ErrorResponseStatusType.DAO_EXCEPTION, e);
        }
    }

    public void update(ProductItem productItem, Connection connection) {
        try (var preparedStatement = connection.prepareStatement(ProductItemSql.UPDATE_SQL)) {
            preparedStatement.setLong(1, productItem.getProduct().getId());
            preparedStatement.setInt(2, productItem.getCount());
            preparedStatement.setLong(3, productItem.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(ErrorResponseStatusType.DAO_EXCEPTION, e);
        }
    }

    @Override
    public List<ProductItem> findAll(EntityExtractor<ProductItem> extractor) {
        try (var connection = ConnectionPool.get();
             var preparedStatement = connection.prepareStatement(ProductItemSql.FIND_ALL_SQL)) {
            var resultSet = preparedStatement.executeQuery();
            List<ProductItem> productItems = new ArrayList<>();
            while (resultSet.next()) {
                productItems.add(extractor.extract(resultSet));
            }
            return productItems;
        } catch (SQLException e) {
            throw new DaoException(ErrorResponseStatusType.DAO_EXCEPTION, e);
        }
    }

    @Override
    public Optional<ProductItem> findById(Long id, EntityExtractor<ProductItem> extractor) {
        try (var connection = ConnectionPool.get()) {
            return findById(id, connection, extractor);
        } catch (SQLException e) {
            throw new DaoException(ErrorResponseStatusType.DAO_EXCEPTION, e);
        }
    }

    public Optional<ProductItem> findById(Long id, Connection connection, EntityExtractor<ProductItem> extractor) {
        try (var preparedStatement = connection.prepareStatement(ProductItemSql.FIND_BY_ID_SQL)) {
            preparedStatement.setLong(1, id);
            var resultSet = preparedStatement.executeQuery();
            ProductItem productItem = null;
            if (resultSet.next()) {
                productItem = extractor.extract(resultSet);
            }
            return Optional.ofNullable(productItem);
        } catch (SQLException e) {
            throw new DaoException(ErrorResponseStatusType.DAO_EXCEPTION, e);
        }
    }

    @Override
    public boolean delete(Long id) {
        try (var connection = ConnectionPool.get()) {
            return delete(id, connection);
        } catch (SQLException e) {
            throw new DaoException(ErrorResponseStatusType.DAO_EXCEPTION, e);
        }
    }

    public boolean delete(Long id, Connection connection) {
        try (var preparedStatement = connection.prepareStatement(ProductItemSql.DELETE_SQL)) {
            preparedStatement.setLong(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(ErrorResponseStatusType.DAO_EXCEPTION, e);
        }
    }

    public static ProductItemDao getInstance() {
        return INSTANCE;
    }

}
