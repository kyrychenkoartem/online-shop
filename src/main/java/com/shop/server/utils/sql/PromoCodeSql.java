package com.shop.server.utils.sql;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PromoCodeSql {

    public static final String DELETE_SQL = """
            DELETE FROM promo_code
            WHERE id = ?
            """;

    public static final String SAVE_SQL = """
            INSERT INTO promo_code (key, value)
            VALUES (?, ?)
            """;

    public static final String UPDATE_SQL = """
            UPDATE promo_code
            SET 
            key = ?,
            value = ?
            WHERE id = ?
            """;

    public static final String UPDATE_VALUE_SQL = """
            UPDATE promo_code
            SET 
            value = ?
            WHERE key = ?
            """;

    public static final String FIND_ALL_SQL = """
            SELECT 
            id, 
            key, 
            value
            FROM promo_code 
            """;

    public static final String FIND_BY_ID_SQL = FIND_ALL_SQL + """
            WHERE id = ?
            """;

    public static final String FIND_BY_KEY_SQL = FIND_ALL_SQL + """
            WHERE key = ?
            """;
}
