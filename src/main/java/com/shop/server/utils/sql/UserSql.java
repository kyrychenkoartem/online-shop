package com.shop.server.utils.sql;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UserSql {

    public static final String DELETE_SQL = """
            DELETE FROM users
            WHERE id = ?
            """;

    public static final String FIND_BY_EMAIL_AND_PASSWORD = """
            SELECT 
            id,
            username,
            email,
            birth_date,
            first_name,
            last_name,
            role,
            gender,
            created_at,
            created_by
            FROM users
            WHERE email = ?
            AND password = ?
            """;

    public static final String SAVE_SQL = """
            INSERT INTO users (username, email, password, birth_date, first_name,
            last_name, role, gender, created_at, created_by, updated_at, updated_by)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, now(), ?, ?, ?)
            """;

    public static final String UPDATE_SQL = """
            UPDATE users
            SET 
            password = ?,
            birth_date = ?,
            first_name = ?,
            last_name = ?,
            gender = ?,
            updated_at = now(),
            updated_by = (SELECT email FROM users WHERE id = ?)
            WHERE id = ?
            """;

    public static final String FIND_ALL_SQL = """
            SELECT 
            id,
            username,
            email,
            birth_date,
            first_name,
            last_name,
            role,
            gender,
            created_at,
            created_by
            FROM users 
            """;

    public static final String FIND_BY_ID_SQL = FIND_ALL_SQL + """
            WHERE id = ?
            """;

    public static final String FIND_BY_EMAIL_SQL = FIND_ALL_SQL + """
            WHERE email = ?
            """;
}
