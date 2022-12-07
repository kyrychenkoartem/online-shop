package com.shop.server.mapper.extractor;

import com.shop.server.model.entity.User;
import com.shop.server.model.type.Gender;
import com.shop.server.model.type.Role;
import java.sql.ResultSet;
import java.sql.SQLException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserExtractor implements EntityExtractor<User> {

    private static final UserExtractor INSTANCE = new UserExtractor();

    @Override
    public User extract(ResultSet resultSet) throws SQLException {
        return User.builder()
                .id(resultSet.getLong("id"))
                .username(resultSet.getString("username"))
                .email(resultSet.getString("email"))
                .birthDate(resultSet.getDate("birth_date").toLocalDate())
                .firstName(resultSet.getString("first_name"))
                .lastName(resultSet.getString("last_name"))
                .role(Role.valueOf(resultSet.getString("role")))
                .gender(Gender.valueOf(resultSet.getString("gender")))
                .createdAt(resultSet.getTimestamp("created_at").toLocalDateTime())
                .createdBy(resultSet.getString("created_by"))
                .build();
    }

    public static UserExtractor getExtractor() {
        return INSTANCE;
    }
}
