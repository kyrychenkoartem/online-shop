package com.shop.server.utils.sql;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PaymentSql {

    public static final String DELETE_SQL = """
            DELETE FROM payment
            WHERE id = ?
            """;

    public static final String SAVE_SQL = """
            INSERT INTO payment (orders_id, bank_card_id, payment_time, payment_status)
            VALUES (?, ?, ?, ?)
            """;

    public static final String UPDATE_SQL = """
            UPDATE payment
            SET 
            payment_time = ?,
            payment_status = ?
            WHERE id = ?
            """;

    public static final String FIND_ALL_SQL = """
            SELECT 
            id,
            orders_id,
            bank_card_id,
            payment_time,
            payment_status
            FROM payment
            """;

    public static final String FIND_BY_ID_SQL = FIND_ALL_SQL + """
            WHERE id = ?
            """;

}
