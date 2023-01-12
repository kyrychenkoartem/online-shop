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
            INSERT INTO cart (price, promo_code_id)
            VALUES (?, ?)
            """;

    public static final String UPDATE_SQL = """
            UPDATE cart
            SET 
            price = ?,
            promo_code_id = ?
            WHERE id = ?
            """;

    public static final String FIND_ALL_SQL = """
            SELECT
                c.id,
                pi.id,
                pi.product_id,
                price,
                promo_code_id
            FROM cart c
                     JOIN product_item pi ON pi.cart_id = c.id
                                    """;

    public static final String FIND_BY_ID_SQL = FIND_ALL_SQL + """
            WHERE c.id = ?
            """;
}
