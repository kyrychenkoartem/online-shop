package com.shop.server.utils.sql;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ProductItemSql {

    public static final String DELETE_SQL = """
            DELETE FROM product_item
            WHERE id = ?
            """;

    public static final String SAVE_SQL = """
            INSERT INTO product_item (product_id, count)
            VALUES (?, ?)
            """;

    public static final String UPDATE_SQL = """
            UPDATE product_item
            SET 
            product_id = ?,
            count = ?
            WHERE id = ?
            """;

    public static final String FIND_ALL_SQL = """
            SELECT 
            id,
            product_id,
            count
            FROM product_item
            """;

    public static final String FIND_BY_ID_SQL = FIND_ALL_SQL + """
            WHERE product_item.id = ?
            """;
}
