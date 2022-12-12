package com.shop.server.utils.sql;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DeliveryAddressSql {

    public static final String DELETE_SQL = """
            DELETE FROM delivery_address
            WHERE id = ?
            """;

    public static final String SAVE_SQL = """
            INSERT INTO delivery_address (orders_id, address, city, province, postal_code)
            VALUES (?, ?, ?, ?, ?)
            """;

    public static final String UPDATE_SQL = """
            UPDATE delivery_address
            SET 
            address = ?,
            city = ?,
            province = ?,
            postal_code = ?
            WHERE id = ?
            """;

    public static final String FIND_ALL_SQL = """
            SELECT 
            id,
            orders_id,
            address, 
            city,
            province,
            postal_code
            FROM delivery_address
            """;

    public static final String FIND_BY_ID_SQL = FIND_ALL_SQL + """
            WHERE id = ?
            """;
}
