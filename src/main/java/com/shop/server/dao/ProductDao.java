package com.shop.server.dao;

import com.shop.server.exception.DaoException;
import com.shop.server.mapper.extractor.EntityExtractor;
import com.shop.server.model.dto.product.ProductFilter;
import com.shop.server.model.entity.Product;
import com.shop.server.model.type.ErrorResponseStatusType;
import com.shop.server.utils.ConnectionPool;
import com.shop.server.utils.sql.ProductSql;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static java.util.stream.Collectors.joining;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductDao implements Dao<Long, Product> {

    private static final ProductDao INSTANCE = new ProductDao();

    @Override
    public Product save(Product product) {
        try (var connection = ConnectionPool.get();
             var preparedStatement = connection.prepareStatement(ProductSql.SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, product.getName());
            preparedStatement.setString(2, product.getDescription());
            preparedStatement.setBigDecimal(3, product.getPrice());
            preparedStatement.setInt(4, product.getQuantities());
            preparedStatement.setString(5, product.getCategory().name());
            preparedStatement.setString(6, product.getMaterial().name());
            preparedStatement.executeUpdate();
            var generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                product.setId(generatedKeys.getLong("id"));
            }
            return product;
        } catch (SQLException e) {
            throw new DaoException(ErrorResponseStatusType.DAO_EXCEPTION, e);
        }
    }

    @Override
    public void update(Product product) {
        try (var connection = ConnectionPool.get();
             var preparedStatement = connection.prepareStatement(ProductSql.UPDATE_SQL)) {
            preparedStatement.setString(1, product.getName());
            preparedStatement.setString(2, product.getDescription());
            preparedStatement.setBigDecimal(3, product.getPrice());
            preparedStatement.setInt(4, product.getQuantities());
            preparedStatement.setString(5, product.getCategory().name());
            preparedStatement.setString(6, product.getMaterial().name());
            preparedStatement.setLong(7, product.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(ErrorResponseStatusType.DAO_EXCEPTION, e);
        }
    }

    @Override
    public List<Product> findAll(EntityExtractor<Product> extractor) {
        try (var connection = ConnectionPool.get();
             var preparedStatement = connection.prepareStatement(ProductSql.FIND_ALL_SQL)) {
            var resultSet = preparedStatement.executeQuery();
            List<Product> products = new ArrayList<>();
            while (resultSet.next()) {
                products.add(extractor.extract(resultSet));
            }
            return products;
        } catch (SQLException e) {
            throw new DaoException(ErrorResponseStatusType.DAO_EXCEPTION, e);
        }
    }

    public List<Product> findAll(ProductFilter productFilter, EntityExtractor<Product> extractor) {
        List<Object> parameters = new ArrayList<>();
        List<String> whereSQL = new ArrayList<>();
        if (productFilter.getName() != null) {
            whereSQL.add("name = ?");
            parameters.add(productFilter.getName());
        }
        if (productFilter.getPrice() != null) {
            whereSQL.add("price LIKE ?");
            parameters.add("%" + productFilter.getPrice() + "%");
        }
        if (productFilter.getCategory() != null) {
            whereSQL.add("category = ?");
            parameters.add(productFilter.getCategory().name());
        }
        if (productFilter.getMaterial() != null) {
            whereSQL.add("material = ?");
            parameters.add(productFilter.getMaterial().name());
        }
        parameters.add(productFilter.getLimit());
        parameters.add(productFilter.getOffset());
        var where = whereSQL.stream()
                .collect(joining(" AND ", "WHERE ", " LIMIT ? OFFSET ? "));
        var sql = ProductSql.FIND_ALL_SQL + where;
        try (var connection = ConnectionPool.get();
             var preparedStatement = connection.prepareStatement(sql)) {
            for (int i = 0; i < parameters.size(); i++) {
                preparedStatement.setObject(i + 1, parameters.get(i));
            }
            System.out.println(preparedStatement);
            var resultSet = preparedStatement.executeQuery();
            List<Product> products = new ArrayList<>();
            while (resultSet.next()) {
                products.add(extractor.extract(resultSet));
            }
            return products;
        } catch (SQLException e) {
            throw new DaoException(ErrorResponseStatusType.DAO_EXCEPTION, e);
        }
    }

    @Override
    public Optional<Product> findById(Long id, EntityExtractor<Product> extractor) {
        try (var connection = ConnectionPool.get()) {
            return findById(id, connection, extractor);
        } catch (SQLException e) {
            throw new DaoException(ErrorResponseStatusType.DAO_EXCEPTION, e);
        }
    }

    public Optional<Product> findById(Long id, Connection connection, EntityExtractor<Product> extractor) {
        try (var preparedStatement = connection.prepareStatement(ProductSql.FIND_BY_ID_SQL)) {
            preparedStatement.setLong(1, id);
            var resultSet = preparedStatement.executeQuery();
            Product product = null;
            if (resultSet.next()) {
                product = extractor.extract(resultSet);
            }
            return Optional.ofNullable(product);
        } catch (SQLException e) {
            throw new DaoException(ErrorResponseStatusType.DAO_EXCEPTION, e);
        }
    }

    public boolean updatePrice(Product product) {
        try (var connection = ConnectionPool.get()) {
            return updatePrice(product, connection);
        } catch (SQLException e) {
            throw new DaoException(ErrorResponseStatusType.DAO_EXCEPTION, e);
        }
    }

    public boolean updatePrice(Product product, Connection connection) {
        try (var preparedStatement = connection.prepareStatement(ProductSql.UPDATE_PRICE_SQL)) {
            preparedStatement.setBigDecimal(1, product.getPrice());
            preparedStatement.setLong(2, product.getId());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(ErrorResponseStatusType.DAO_EXCEPTION, e);
        }
    }

    public boolean updateQuantities(Product product) {
        try (var connection = ConnectionPool.get()) {
            return updateQuantities(product, connection);
        } catch (SQLException e) {
            throw new DaoException(ErrorResponseStatusType.DAO_EXCEPTION, e);
        }
    }

    public boolean updateQuantities(Product product, Connection connection) {
        try (var preparedStatement = connection.prepareStatement(ProductSql.UPDATE_QUANTITIES_SQL)) {
            preparedStatement.setInt(1, product.getQuantities());
            preparedStatement.setLong(2, product.getId());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(ErrorResponseStatusType.DAO_EXCEPTION, e);
        }
    }

    public boolean takeProduct(Long id, Integer quantities) {
        try (var connection = ConnectionPool.get()) {
            return takeProduct(id, quantities, connection);
        } catch (SQLException e) {
            throw new DaoException(ErrorResponseStatusType.DAO_EXCEPTION, e);
        }
    }

    public boolean takeProduct(Long id, Integer quantities, Connection connection) {
        try (var preparedStatement = connection.prepareStatement(ProductSql.TAKE_QUANTITIES_SQL)) {
            preparedStatement.setInt(1, quantities);
            preparedStatement.setLong(2, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(ErrorResponseStatusType.DAO_EXCEPTION, e);
        }
    }

    public boolean putProduct(Long id, Integer quantities) {
        try (var connection = ConnectionPool.get()) {
            return putProduct(id, quantities, connection);
        } catch (SQLException e) {
            throw new DaoException(ErrorResponseStatusType.DAO_EXCEPTION, e);
        }
    }

    public boolean putProduct(Long id, Integer quantities, Connection connection) {
        try (var preparedStatement = connection.prepareStatement(ProductSql.PUT_QUANTITIES_SQL)) {
            preparedStatement.setInt(1, quantities);
            preparedStatement.setLong(2, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(ErrorResponseStatusType.DAO_EXCEPTION, e);
        }
    }


    @Override
    public boolean delete(Long id) {
        try (var connection = ConnectionPool.get();
             var preparedStatement = connection.prepareStatement(ProductSql.DELETE_SQL)) {
            preparedStatement.setLong(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(ErrorResponseStatusType.DAO_EXCEPTION, e);
        }
    }

    public boolean isProductQuantitiesEnough(Long id, Integer quantities) {
        try (var connection = ConnectionPool.get()) {
            return isProductQuantitiesEnough(id, quantities, connection);
        } catch (SQLException e) {
            throw new DaoException(ErrorResponseStatusType.DAO_EXCEPTION, e);
        }
    }

    public boolean isProductQuantitiesEnough(Long id, Integer quantities, Connection connection) {
        try (var preparedStatement = connection.prepareStatement(ProductSql.CHECK_QUANTITIES_SQL)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            int productQuantities = 0;
            while (resultSet.next()) {
                productQuantities = resultSet.getInt("quantities");
            }
            return productQuantities > quantities || productQuantities > 0;
        } catch (SQLException e) {
            throw new DaoException(ErrorResponseStatusType.DAO_EXCEPTION, e);
        }
    }

    public static ProductDao getInstance() {
        return INSTANCE;
    }

}
