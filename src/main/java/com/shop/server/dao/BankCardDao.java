package com.shop.server.dao;

import com.shop.server.exception.DaoException;
import com.shop.server.model.entity.BankCard;
import com.shop.server.model.type.CardType;
import com.shop.server.model.type.ErrorResponseStatusType;
import com.shop.server.utils.ConnectionPool;
import com.shop.server.utils.sql.BankCardSql;
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
public class BankCardDao implements Dao<Long, BankCard> {

    private static final BankCardDao INSTANCE = new BankCardDao();
    private final UserDao userDao = UserDao.getInstance();

    @Override
    public BankCard save(BankCard bankCard) {
        try (var connection = ConnectionPool.get();
             var preparedStatement = connection.prepareStatement(BankCardSql.SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, bankCard.getUser().getId());
            preparedStatement.setString(2, bankCard.getCardNumber());
            preparedStatement.setString(3, bankCard.getExpiryDate());
            preparedStatement.setString(4, bankCard.getBank());
            preparedStatement.setString(5, bankCard.getCvv());
            preparedStatement.setString(6, bankCard.getCardType().name());
            preparedStatement.executeUpdate();
            var generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                bankCard.setId(generatedKeys.getLong("id"));
            }
            return bankCard;
        } catch (SQLException e) {
            throw new DaoException(ErrorResponseStatusType.DAO_EXCEPTION, e);
        }
    }

    @Override
    public void update(BankCard bankCard) {
        try (var connection = ConnectionPool.get();
             var preparedStatement = connection.prepareStatement(BankCardSql.UPDATE_SQL)) {
            preparedStatement.setLong(1, bankCard.getUser().getId());
            preparedStatement.setString(2, bankCard.getCardNumber());
            preparedStatement.setString(3, bankCard.getExpiryDate());
            preparedStatement.setString(4, bankCard.getBank());
            preparedStatement.setString(5, bankCard.getCvv());
            preparedStatement.setString(6, bankCard.getCardType().name());
            preparedStatement.setLong(7, bankCard.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(ErrorResponseStatusType.DAO_EXCEPTION, e);
        }
    }

    @Override
    public List<BankCard> findAll() {
        try (var connection = ConnectionPool.get();
             var preparedStatement = connection.prepareStatement(BankCardSql.FIND_ALL_SQL)) {
            var resultSet = preparedStatement.executeQuery();
            List<BankCard> bankCards = new ArrayList<>();
            while (resultSet.next()) {
                bankCards.add(buildBankCard(resultSet));
            }
            return bankCards;
        } catch (SQLException e) {
            throw new DaoException(ErrorResponseStatusType.DAO_EXCEPTION, e);
        }
    }

    @Override
    public Optional<BankCard> findById(Long id) {
        try (var connection = ConnectionPool.get()) {
            return findById(id, connection);
        } catch (SQLException e) {
            throw new DaoException(ErrorResponseStatusType.DAO_EXCEPTION, e);
        }
    }

    public Optional<BankCard> findById(Long id, Connection connection) {
        try (var preparedStatement = connection.prepareStatement(BankCardSql.FIND_BY_ID_SQL)) {
            preparedStatement.setLong(1, id);
            var resultSet = preparedStatement.executeQuery();
            BankCard bankCard = null;
            if (resultSet.next()) {
                bankCard = buildBankCard(resultSet);
            }
            return Optional.ofNullable(bankCard);
        } catch (SQLException e) {
            throw new DaoException(ErrorResponseStatusType.DAO_EXCEPTION, e);
        }
    }

    @Override
    public boolean delete(Long id) {
        try (var connection = ConnectionPool.get();
             var preparedStatement = connection.prepareStatement(BankCardSql.DELETE_SQL)) {
            preparedStatement.setLong(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(ErrorResponseStatusType.DAO_EXCEPTION, e);
        }
    }

    public static BankCardDao getInstance() {
        return INSTANCE;
    }

    private BankCard buildBankCard(ResultSet resultSet) throws SQLException {
        return BankCard.builder()
                .id(resultSet.getLong("id"))
                .user(userDao.findById(resultSet.getLong("users_id"),
                                resultSet.getStatement().getConnection())
                        .orElseThrow(() -> new DaoException(ErrorResponseStatusType.DAO_EXCEPTION)))
                .cardNumber(resultSet.getString("card_number"))
                .expiryDate(resultSet.getString("expiry_date"))
                .bank(resultSet.getString("bank"))
                .cvv(resultSet.getString("cvv"))
                .cardType(CardType.valueOf(resultSet.getString("card_type")))
                .build();
    }
}
