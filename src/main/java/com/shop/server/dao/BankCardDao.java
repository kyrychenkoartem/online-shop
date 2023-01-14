package com.shop.server.dao;

import com.shop.server.exception.DaoException;
import com.shop.server.mapper.extractor.EntityExtractor;
import com.shop.server.model.entity.BankCard;
import com.shop.server.model.type.ErrorResponseStatusType;
import com.shop.server.utils.ConnectionPool;
import com.shop.server.utils.sql.BankCardSql;
import java.sql.Connection;
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

    @Override
    public BankCard save(BankCard bankCard) {
        try (var connection = ConnectionPool.get()) {
            return save(bankCard, connection);
        } catch (SQLException e) {
            throw new DaoException(ErrorResponseStatusType.DAO_EXCEPTION, e);
        }
    }

    public BankCard save(BankCard bankCard, Connection connection) {
        try (var preparedStatement = connection.prepareStatement(BankCardSql.SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
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

    public BankCard save(BankCard bankCard, Long userId) {
        try (var connection = ConnectionPool.get()) {
            return save(bankCard, userId, connection);
        } catch (SQLException e) {
            throw new DaoException(ErrorResponseStatusType.DAO_EXCEPTION, e);
        }
    }

    public BankCard save(BankCard bankCard, Long userId, Connection connection) {
        try (var preparedStatement = connection.prepareStatement(BankCardSql.SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, userId);
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
        try (var connection = ConnectionPool.get()) {
            update(bankCard, connection);
        } catch (SQLException e) {
            throw new DaoException(ErrorResponseStatusType.DAO_EXCEPTION, e);
        }
    }

    public void update(BankCard bankCard, Connection connection) {
        try (var preparedStatement = connection.prepareStatement(BankCardSql.UPDATE_SQL)) {
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
    public List<BankCard> findAll(EntityExtractor<BankCard> extractor) {
        try (var connection = ConnectionPool.get();
             var preparedStatement = connection.prepareStatement(BankCardSql.FIND_ALL_SQL)) {
            var resultSet = preparedStatement.executeQuery();
            List<BankCard> bankCards = new ArrayList<>();
            while (resultSet.next()) {
                bankCards.add(extractor.extract(resultSet));
            }
            return bankCards;
        } catch (SQLException e) {
            throw new DaoException(ErrorResponseStatusType.DAO_EXCEPTION, e);
        }
    }

    @Override
    public Optional<BankCard> findById(Long id, EntityExtractor<BankCard> extractor) {
        try (var connection = ConnectionPool.get()) {
            return findById(id, connection, extractor);
        } catch (SQLException e) {
            throw new DaoException(ErrorResponseStatusType.DAO_EXCEPTION, e);
        }
    }

    public Optional<BankCard> findById(Long id, Connection connection, EntityExtractor<BankCard> extractor) {
        try (var preparedStatement = connection.prepareStatement(BankCardSql.FIND_BY_ID_SQL)) {
            preparedStatement.setLong(1, id);
            var resultSet = preparedStatement.executeQuery();
            BankCard bankCard = null;
            if (resultSet.next()) {
                bankCard = extractor.extract(resultSet);
            }
            return Optional.ofNullable(bankCard);
        } catch (SQLException e) {
            throw new DaoException(ErrorResponseStatusType.DAO_EXCEPTION, e);
        }
    }

    public Optional<BankCard> findByUserId(BankCard card, Long userId, EntityExtractor<BankCard> extractor) {
        try (var connection = ConnectionPool.get()) {
            return findByUserId(card, userId, connection, extractor);
        } catch (SQLException e) {
            throw new DaoException(ErrorResponseStatusType.DAO_EXCEPTION, e);
        }
    }

    public Optional<BankCard> findByUserId(BankCard card, Long userId, Connection connection, EntityExtractor<BankCard> extractor) {
        try (var preparedStatement = connection.prepareStatement(BankCardSql.FIND_BY_USER_ID_SQL)) {
            preparedStatement.setLong(1, userId);
            preparedStatement.setString(2, card.getCardNumber());
            preparedStatement.setString(3, card.getExpiryDate());
            preparedStatement.setString(4, card.getBank());
            preparedStatement.setString(5, card.getCvv());
            preparedStatement.setString(6, card.getCardType().name());
            var resultSet = preparedStatement.executeQuery();
            BankCard bankCard = null;
            if (resultSet.next()) {
                bankCard = extractor.extract(resultSet);
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

}
