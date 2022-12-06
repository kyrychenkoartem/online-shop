package com.shop.server.dao;

import com.shop.server.exception.ConnectionException;
import com.shop.server.exception.DaoException;
import com.shop.server.model.entity.Cart;
import com.shop.server.model.type.ErrorResponseStatusType;
import com.shop.server.utils.ConnectionPool;
import com.shop.server.utils.sql.CartSql;
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
public class CartDao implements Dao<Long, Cart> {

    private static final CartDao INSTANCE = new CartDao();
    private final ProductItemDao productItemDao = ProductItemDao.getInstance();
    private final PromoCodeDao promoCodeDao = PromoCodeDao.getInstance();

    @Override
    public Cart save(Cart cart) {
        try (var connection = ConnectionPool.get();
             var preparedStatement = connection.prepareStatement(CartSql.SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, cart.getProductItem().getId());
            preparedStatement.setBigDecimal(2, cart.getPrice());
            preparedStatement.setLong(3, cart.getPromoCode().getId());
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
        try (var connection = ConnectionPool.get();
             var preparedStatement = connection.prepareStatement(CartSql.UPDATE_SQL)) {
            preparedStatement.setLong(1, cart.getProductItem().getId());
            preparedStatement.setBigDecimal(2, cart.getPrice());
            preparedStatement.setLong(3, cart.getPromoCode().getId());
            preparedStatement.setLong(4, cart.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(ErrorResponseStatusType.DAO_EXCEPTION, e);
        }
    }

    @Override
    public List<Cart> findAll() {
        try (var connection = ConnectionPool.get();
             var preparedStatement = connection.prepareStatement(CartSql.FIND_ALL_SQL)) {
            var resultSet = preparedStatement.executeQuery();
            List<Cart> carts = new ArrayList<>();
            while (resultSet.next()) {
                carts.add(buildCart(resultSet));
            }
            return carts;
        } catch (SQLException e) {
            throw new DaoException(ErrorResponseStatusType.DAO_EXCEPTION, e);
        }
    }

    @Override
    public Optional<Cart> findById(Long id) {
        try (var connection = ConnectionPool.get()) {
            return findById(id, connection);
        } catch (SQLException e) {
            throw new DaoException(ErrorResponseStatusType.DAO_EXCEPTION, e);
        }
    }

    public Optional<Cart> findById(Long id, Connection connection) {
        try (var preparedStatement = connection.prepareStatement(CartSql.FIND_BY_ID_SQL)) {
            preparedStatement.setLong(1, id);
            var resultSet = preparedStatement.executeQuery();
            Cart cart = null;
            if (resultSet.next()) {
                cart = buildCart(resultSet);
            }
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

    private Cart buildCart(ResultSet resultSet) throws SQLException {
        return Cart.builder()
                .id(resultSet.getLong("id"))
                .productItem(productItemDao.findById(resultSet.getLong("product_item_id"),
                                resultSet.getStatement().getConnection())
                        .orElseThrow(() -> new ConnectionException(ErrorResponseStatusType.DAO_EXCEPTION)))
                .price(resultSet.getBigDecimal("price"))
                .promoCode(promoCodeDao.findById(resultSet.getLong("promo_code_id"),
                                resultSet.getStatement().getConnection())
                        .orElseThrow(() -> new ConnectionException(ErrorResponseStatusType.DAO_EXCEPTION)))
                .build();
    }
}
