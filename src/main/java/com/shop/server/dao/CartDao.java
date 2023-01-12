package com.shop.server.dao;

import com.shop.server.exception.DaoException;
import com.shop.server.mapper.extractor.EntityExtractor;
import com.shop.server.model.entity.Cart;
import com.shop.server.model.entity.ProductItem;
import com.shop.server.model.type.ErrorResponseStatusType;
import com.shop.server.utils.ConnectionPool;
import com.shop.server.utils.sql.CartSql;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CartDao implements Dao<Long, Cart> {

    private static final Long DEFAULT_PROMO_CODE_ID = 1L;

    private static final CartDao INSTANCE = new CartDao();

    @Override
    public Cart save(Cart cart) {
        try (var connection = ConnectionPool.get()) {
            return save(cart, connection);
        } catch (SQLException e) {
            throw new DaoException(ErrorResponseStatusType.DAO_EXCEPTION, e);
        }
    }

    public Cart save(Cart cart, Connection connection) {
        try (var preparedStatement = connection.prepareStatement(CartSql.SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setBigDecimal(1, cart.getPrice());
            preparedStatement.setLong(2, DEFAULT_PROMO_CODE_ID);
            preparedStatement.executeUpdate();
            var generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                cart.setId(generatedKeys.getLong("id"));
            }
            return cart;
        } catch (SQLException e) {
            throw new DaoException(ErrorResponseStatusType.DAO_EXCEPTION, e);
        }
    }

    @Override
    public void update(Cart cart) {
        try (var connection = ConnectionPool.get()) {
            update(cart, connection);
        } catch (SQLException e) {
            throw new DaoException(ErrorResponseStatusType.DAO_EXCEPTION, e);
        }
    }

    public void update(Cart cart, Connection connection) {
        try (var preparedStatement = connection.prepareStatement(CartSql.UPDATE_SQL)) {
            preparedStatement.setBigDecimal(1, cart.getPrice());
            preparedStatement.setLong(2, cart.getPromoCode().getId());
            preparedStatement.setLong(3, cart.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(ErrorResponseStatusType.DAO_EXCEPTION, e);
        }
    }

    @Override
    public List<Cart> findAll(EntityExtractor<Cart> extractor) {
        try (var connection = ConnectionPool.get();
             var preparedStatement = connection.prepareStatement(CartSql.FIND_ALL_SQL)) {
            var resultSet = preparedStatement.executeQuery();
            List<Cart> carts = new ArrayList<>();
            while (resultSet.next()) {
                extract(extractor, resultSet, carts);
            }
            carts.sort(Comparator.comparing(Cart::getId));
            return carts;
        } catch (SQLException e) {
            throw new DaoException(ErrorResponseStatusType.DAO_EXCEPTION, e);
        }
    }

    @Override
    public Optional<Cart> findById(Long id, EntityExtractor<Cart> extractor) {
        try (var connection = ConnectionPool.get()) {
            return findById(id, connection, extractor);
        } catch (SQLException e) {
            throw new DaoException(ErrorResponseStatusType.DAO_EXCEPTION, e);
        }
    }

    public Optional<Cart> findById(Long id, Connection connection, EntityExtractor<Cart> extractor) {
        try (var preparedStatement = connection.prepareStatement(CartSql.FIND_BY_ID_SQL)) {
            preparedStatement.setLong(1, id);
            var resultSet = preparedStatement.executeQuery();
            Cart cart = null;
            List<ProductItem> productItems = new ArrayList<>();
            while (resultSet.next()) {
                cart = extract(extractor, resultSet, cart, productItems);
            }
            Objects.requireNonNull(cart).setProductItems(productItems);
            return Optional.ofNullable(cart);
        } catch (SQLException e) {
            throw new DaoException(ErrorResponseStatusType.DAO_EXCEPTION, e);
        }
    }

    @Override
    public boolean delete(Long id) {
        try (var connection = ConnectionPool.get();
             var preparedStatement = connection.prepareStatement(CartSql.DELETE_SQL)) {
            preparedStatement.setLong(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(ErrorResponseStatusType.DAO_EXCEPTION, e);
        }
    }

    public static CartDao getInstance() {
        return INSTANCE;
    }


    private void extract(EntityExtractor<Cart> extractor, ResultSet resultSet, List<Cart> carts) throws SQLException {
        Cart extractedCart = extractor.extract(resultSet);
        if (carts.contains(extractedCart)) {
            Cart remove = carts.remove(carts.indexOf(extractedCart));
            ArrayList<ProductItem> productItems = new ArrayList<>(remove.getProductItems());
            productItems.add(extractedCart.getProductItems().get(0));
            remove.setProductItems(productItems);
            carts.add(remove);
        } else {
            carts.add(extractedCart);
        }
    }

    private Cart extract(EntityExtractor<Cart> extractor, ResultSet resultSet, Cart cart, List<ProductItem> productItems) throws SQLException {
        Cart temporaryCart;
        if (!ObjectUtils.isEmpty(cart)) {
            temporaryCart = extractor.extract(resultSet);
            productItems.addAll(temporaryCart.getProductItems());
        }
        if (ObjectUtils.isEmpty(cart)) {
            cart = extractor.extract(resultSet);
            productItems.addAll(cart.getProductItems());
        }
        return cart;
    }
}
