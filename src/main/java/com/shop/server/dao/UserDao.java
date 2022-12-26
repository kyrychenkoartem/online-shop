package com.shop.server.dao;

import com.shop.server.exception.DaoException;
import com.shop.server.mapper.extractor.EntityExtractor;
import com.shop.server.model.entity.User;
import com.shop.server.model.type.ErrorResponseStatusType;
import com.shop.server.utils.ConnectionPool;
import com.shop.server.utils.sql.UserSql;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserDao implements Dao<Long, User> {

    private static final UserDao INSTANCE = new UserDao();

    @Override
    public User save(User user) {
        try (var connection = ConnectionPool.get();
             var preparedStatement = connection.prepareStatement(UserSql.SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setDate(4, Date.valueOf(user.getBirthDate()));
            preparedStatement.setString(5, user.getFirstName());
            preparedStatement.setString(6, user.getLastName());
            preparedStatement.setString(7, user.getRole().name());
            preparedStatement.setString(8, user.getGender().name());
            preparedStatement.setString(9, user.getUsername());
            preparedStatement.setTimestamp(10, null);
            preparedStatement.setString(11, null);
            preparedStatement.executeUpdate();
            var generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                user.setId(generatedKeys.getLong("id"));
            }
            return user;
        } catch (SQLException e) {
            throw new DaoException(ErrorResponseStatusType.DAO_EXCEPTION, e);
        }
    }

    @Override
    public void update(User user) {
        try (var connection = ConnectionPool.get();
             var preparedStatement = connection.prepareStatement(UserSql.UPDATE_SQL)) {
            preparedStatement.setString(1, user.getPassword());
            preparedStatement.setDate(2, Date.valueOf(user.getBirthDate()));
            preparedStatement.setString(3, user.getFirstName());
            preparedStatement.setString(4, user.getLastName());
            preparedStatement.setString(5, user.getGender().name());
            preparedStatement.setLong(6, user.getId());
            preparedStatement.setLong(7, user.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(ErrorResponseStatusType.DAO_EXCEPTION, e);
        }
    }

    @Override
    public List<User> findAll(EntityExtractor<User> extractor) {
        try (var connection = ConnectionPool.get();
             var preparedStatement = connection.prepareStatement(UserSql.FIND_ALL_SQL)) {
            var resultSet = preparedStatement.executeQuery();
            List<User> users = new ArrayList<>();
            while (resultSet.next()) {
                users.add(extractor.extract(resultSet));
            }
            return users;
        } catch (SQLException e) {
            throw new DaoException(ErrorResponseStatusType.DAO_EXCEPTION, e);
        }
    }

    @Override
    public Optional<User> findById(Long id, EntityExtractor<User> extractor) {
        try (var connection = ConnectionPool.get()) {
            return findById(id, connection, extractor);
        } catch (SQLException e) {
            throw new DaoException(ErrorResponseStatusType.DAO_EXCEPTION, e);
        }
    }

    public Optional<User> findById(Long id, Connection connection, EntityExtractor<User> extractor) {
        try (var preparedStatement = connection.prepareStatement(UserSql.FIND_BY_ID_SQL)) {
            preparedStatement.setLong(1, id);
            var resultSet = preparedStatement.executeQuery();
            User user = null;
            if (resultSet.next()) {
                user = extractor.extract(resultSet);
            }
            return Optional.ofNullable(user);
        } catch (SQLException e) {
            throw new DaoException(ErrorResponseStatusType.DAO_EXCEPTION, e);
        }
    }

    public Optional<User> findByEmail(String email, EntityExtractor<User> extractor) {
        try (var connection = ConnectionPool.get();
             var preparedStatement = connection.prepareStatement(UserSql.FIND_BY_EMAIL_SQL)) {
            preparedStatement.setString(1, email);
            var resultSet = preparedStatement.executeQuery();
            User user = null;
            if (resultSet.next()) {
                user = extractor.extract(resultSet);
            }
            return Optional.ofNullable(user);
        } catch (SQLException e) {
            throw new DaoException(ErrorResponseStatusType.DAO_EXCEPTION, e);
        }
    }

    @Override
    public boolean delete(Long id) {
        try (var connection = ConnectionPool.get();
             var preparedStatement = connection.prepareStatement(UserSql.DELETE_SQL)) {
            preparedStatement.setLong(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(ErrorResponseStatusType.DAO_EXCEPTION, e);
        }
    }

    public static UserDao getInstance() {
        return INSTANCE;
    }
}
