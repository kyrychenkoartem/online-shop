package com.shop.server.utils;

import com.shop.server.exception.TransactionException;
import com.shop.server.model.type.ErrorResponseStatusType;
import java.sql.Connection;
import java.sql.SQLException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TransactionManager {

    private static final Logger log = LoggerFactory.getLogger(TransactionManager.class);

    public static void startTransaction(Connection connection) {
        try {
            log.info("[startTransaction] invoked with connection = [{}]", connection);
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            throw new TransactionException(ErrorResponseStatusType.TRANSACTION_EXCEPTION, e.getMessage());
        }
    }

    public static void commitTransaction(Connection connection) {
        try {
            log.info("[commitTransaction] invoked with connection = [{}]", connection);
            connection.commit();
        } catch (SQLException e) {
            throw new TransactionException(ErrorResponseStatusType.TRANSACTION_EXCEPTION, e.getMessage());
        }
    }

    public static void rollbackTransaction(Connection connection) {
        try {
            log.info("[rollbackTransaction] invoked with connection = [{}]", connection);
            connection.rollback();
        } catch (SQLException e) {
            throw new TransactionException(ErrorResponseStatusType.TRANSACTION_EXCEPTION, e.getMessage());
        }
    }

    public static void finishTransaction(Connection connection) {
        try {
            log.info("[finishTransaction] invoked with connection = [{}]", connection);
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            throw new TransactionException(ErrorResponseStatusType.TRANSACTION_EXCEPTION, e.getMessage());
        }
    }
}
