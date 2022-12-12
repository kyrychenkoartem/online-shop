package com.shop.server.utils.sql;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ProductSql {

    public static final String DELETE_SQL = """
            DELETE FROM product
            WHERE id = ?
            """;

    public static final String SAVE_SQL = """
            INSERT INTO product (name, description, price, quantities, category, material)
            VALUES (?, ?, ?, ?, ?, ?)
            """;

    public static final String UPDATE_SQL = """
            UPDATE product
            SET 
            name = ?,
            description = ?,
            price = ?,
            quantities = ?,
            category = ?,
            material = ?
            WHERE id = ?
            """;

    public static final String UPDATE_PRICE_SQL = """
            UPDATE product
            SET 
            price = ?
            WHERE id = ?
            """;

    public static final String UPDATE_QUANTITIES_SQL = """
            UPDATE product
            SET 
            quantities = ?
            WHERE id = ?
            """;

    public static final String FIND_ALL_SQL = """
            SELECT 
            id,
            name,
            description,
            price,
            quantities,
            category,
            material
            FROM product 
            """;

    public static final String FIND_BY_ID_SQL = FIND_ALL_SQL + """
            WHERE id = ?
            """;
}