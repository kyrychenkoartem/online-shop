package com.shop.server.utils.sql;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class OrderSql {

    public static final String DELETE_SQL = """
            DELETE FROM orders
            WHERE id = ?
            """;

    public static final String SAVE_SQL = """
            INSERT INTO orders (cart_id, users_id, created_at, closed_at, status)
            VALUES (?, ?, now(), ?, ?)
            """;

    public static final String UPDATE_SQL = """
            UPDATE orders
            SET 
            closed_at = ?,
            status = ?
            WHERE id = ?
            """;

    public static final String FIND_ALL_SQL = """
            SELECT 
            id,
            cart_id,   
            users_id,
            created_at,
            status
            FROM orders
            """;

    public static final String FIND_BY_ID_SQL = FIND_ALL_SQL + """
            WHERE id = ?
            """;
}
