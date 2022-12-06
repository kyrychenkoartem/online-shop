package com.shop.server.utils.sql;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CartSql {

    public static final String DELETE_SQL = """
            DELETE FROM cart
            WHERE id = ?
            """;

    public static final String SAVE_SQL = """
            INSERT INTO cart (product_item_id, price, promo_code_id)
            VALUES (?, ?, ?)
            """;

    public static final String UPDATE_SQL = """
            UPDATE cart
            SET 
            product_item_id = ?,
            price = ?,
            promo_code_id = ?
            WHERE id = ?
            """;

    public static final String FIND_ALL_SQL = """
            SELECT 
            id,
            product_item_id,
            price,
            promo_code_id
            FROM cart
            """;

    public static final String FIND_BY_ID_SQL = FIND_ALL_SQL + """
            WHERE id = ?
            """;
}
