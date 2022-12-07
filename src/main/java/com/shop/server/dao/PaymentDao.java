package com.shop.server.dao;

import com.shop.server.exception.DaoException;
import com.shop.server.mapper.extractor.EntityExtractor;
import com.shop.server.model.entity.Payment;
import com.shop.server.model.type.ErrorResponseStatusType;
import com.shop.server.utils.ConnectionPool;
import com.shop.server.utils.sql.PaymentSql;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PaymentDao implements Dao<Long, Payment> {
    private static final PaymentDao INSTANCE = new PaymentDao();

    @Override
    public Payment save(Payment payment) {
        try (var connection = ConnectionPool.get();
             var preparedStatement = connection.prepareStatement(PaymentSql.SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, payment.getOrder().getId());
            preparedStatement.setLong(2, payment.getBankCard().getId());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(payment.getPaymentTime()));
            preparedStatement.setString(4, payment.getPaymentStatus().name());
            preparedStatement.executeUpdate();
            var generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                payment.setId(generatedKeys.getLong("id"));
            }
            return payment;
        } catch (SQLException e) {
            throw new DaoException(ErrorResponseStatusType.DAO_EXCEPTION, e);
        }
    }

    @Override
    public void update(Payment payment) {
        try (var connection = ConnectionPool.get();
             var preparedStatement = connection.prepareStatement(PaymentSql.UPDATE_SQL)) {
            preparedStatement.setTimestamp(1, Timestamp.valueOf(payment.getPaymentTime()));
            preparedStatement.setString(2, payment.getPaymentStatus().name());
            preparedStatement.setLong(3, payment.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(ErrorResponseStatusType.DAO_EXCEPTION, e);
        }
    }

    @Override
    public List<Payment> findAll(EntityExtractor<Payment> extractor) {
        try (var connection = ConnectionPool.get();
             var preparedStatement = connection.prepareStatement(PaymentSql.FIND_ALL_SQL)) {
            var resultSet = preparedStatement.executeQuery();
            List<Payment> payments = new ArrayList<>();
            while (resultSet.next()) {
                payments.add(extractor.extract(resultSet));
            }
            return payments;
        } catch (SQLException e) {
            throw new DaoException(ErrorResponseStatusType.DAO_EXCEPTION, e);
        }
    }

    @Override
    public Optional<Payment> findById(Long id, EntityExtractor<Payment> extractor) {
        try (var connection = ConnectionPool.get()) {
            return findById(id, connection, extractor);
        } catch (SQLException e) {
            throw new DaoException(ErrorResponseStatusType.DAO_EXCEPTION, e);
        }
    }

    public Optional<Payment> findById(Long id, Connection connection, EntityExtractor<Payment> extractor) {
        try (var preparedStatement = connection.prepareStatement(PaymentSql.FIND_BY_ID_SQL)) {
            preparedStatement.setLong(1, id);
            var resultSet = preparedStatement.executeQuery();
            Payment payment = null;
            if (resultSet.next()) {
                payment = extractor.extract(resultSet);
            }
            return Optional.ofNullable(payment);
        } catch (SQLException e) {
            throw new DaoException(ErrorResponseStatusType.DAO_EXCEPTION, e);
        }
    }

    @Override
    public boolean delete(Long id) {
        try (var connection = ConnectionPool.get();
             var preparedStatement = connection.prepareStatement(PaymentSql.DELETE_SQL)) {
            preparedStatement.setLong(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(ErrorResponseStatusType.DAO_EXCEPTION, e);
        }
    }

    public static PaymentDao getInstance() {
        return INSTANCE;
    }

}
