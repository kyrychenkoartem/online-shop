package com.shop.server.dao;

import com.shop.server.exception.DaoException;
import com.shop.server.mapper.extractor.EntityExtractor;
import com.shop.server.model.entity.PromoCode;
import com.shop.server.model.type.ErrorResponseStatusType;
import com.shop.server.utils.ConnectionPool;
import com.shop.server.utils.sql.PromoCodeSql;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PromoCodeDao implements Dao<Long, PromoCode> {

    private static final PromoCodeDao INSTANCE = new PromoCodeDao();

    @Override
    public PromoCode save(PromoCode promoCode) {
        try (var connection = ConnectionPool.get()) {
            return save(promoCode, connection);
        } catch (SQLException e) {
            throw new DaoException(ErrorResponseStatusType.DAO_EXCEPTION, e);
        }
    }

    public PromoCode save(PromoCode promoCode, Connection connection) {
        try (var preparedStatement = connection.prepareStatement(PromoCodeSql.SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, promoCode.getKey());
            preparedStatement.setInt(2, promoCode.getValue());
            preparedStatement.executeUpdate();
            var generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                promoCode.setId(generatedKeys.getLong("id"));
            }
            return promoCode;
        } catch (SQLException e) {
            throw new DaoException(ErrorResponseStatusType.DAO_EXCEPTION, e);
        }
    }

    @Override
    public void update(PromoCode promoCode) {
        try (var connection = ConnectionPool.get();
             var preparedStatement = connection.prepareStatement(PromoCodeSql.UPDATE_SQL)) {
            preparedStatement.setString(1, promoCode.getKey());
            preparedStatement.setInt(2, promoCode.getValue());
            preparedStatement.setLong(3, promoCode.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(ErrorResponseStatusType.DAO_EXCEPTION, e);
        }
    }

    public void updateValue(PromoCode promoCode) {
        try (var connection = ConnectionPool.get();
             var preparedStatement = connection.prepareStatement(PromoCodeSql.UPDATE_VALUE_SQL)) {
            preparedStatement.setInt(1, promoCode.getValue());
            preparedStatement.setString(2, promoCode.getKey());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(ErrorResponseStatusType.DAO_EXCEPTION, e);
        }
    }

    @Override
    public List<PromoCode> findAll(EntityExtractor<PromoCode> extractor) {
        try (var connection = ConnectionPool.get();
             var preparedStatement = connection.prepareStatement(PromoCodeSql.FIND_ALL_SQL)) {
            var resultSet = preparedStatement.executeQuery();
            List<PromoCode> promoCodes = new ArrayList<>();
            while (resultSet.next()) {
                promoCodes.add(extractor.extract(resultSet));
            }
            return promoCodes;
        } catch (SQLException e) {
            throw new DaoException(ErrorResponseStatusType.DAO_EXCEPTION, e);
        }
    }

    @Override
    public Optional<PromoCode> findById(Long id, EntityExtractor<PromoCode> extractor) {
        try (var connection = ConnectionPool.get()) {
            return findById(id, connection, extractor);
        } catch (SQLException e) {
            throw new DaoException(ErrorResponseStatusType.DAO_EXCEPTION, e);
        }
    }

    public Optional<PromoCode> findById(Long id, Connection connection, EntityExtractor<PromoCode> extractor) {
        try (var preparedStatement = connection.prepareStatement(PromoCodeSql.FIND_BY_ID_SQL)) {
            preparedStatement.setLong(1, id);
            var resultSet = preparedStatement.executeQuery();
            PromoCode promoCode = null;
            if (resultSet.next()) {
                promoCode = extractor.extract(resultSet);
            }
            return Optional.ofNullable(promoCode);
        } catch (SQLException e) {
            throw new DaoException(ErrorResponseStatusType.DAO_EXCEPTION, e);
        }
    }

    public Optional<PromoCode> findByKey(String key, EntityExtractor<PromoCode> extractor) {
        try (var connection = ConnectionPool.get()) {
            return findByKey(key, connection, extractor);
        } catch (SQLException e) {
            throw new DaoException(ErrorResponseStatusType.DAO_EXCEPTION, e);
        }
    }

    public Optional<PromoCode> findByKey(String key, Connection connection, EntityExtractor<PromoCode> extractor) {
        try (var preparedStatement = connection.prepareStatement(PromoCodeSql.FIND_BY_KEY_SQL)) {
            preparedStatement.setString(1, key);
            var resultSet = preparedStatement.executeQuery();
            PromoCode promoCode = null;
            if (resultSet.next()) {
                promoCode = extractor.extract(resultSet);
            }
            return Optional.ofNullable(promoCode);
        } catch (SQLException e) {
            throw new DaoException(ErrorResponseStatusType.DAO_EXCEPTION, e);
        }
    }

    @Override
    public boolean delete(Long id) {
        try (var connection = ConnectionPool.get();
             var preparedStatement = connection.prepareStatement(PromoCodeSql.DELETE_SQL)) {
            preparedStatement.setLong(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(ErrorResponseStatusType.DAO_EXCEPTION, e);
        }
    }

    public static PromoCodeDao getInstance() {
        return INSTANCE;
    }

}
