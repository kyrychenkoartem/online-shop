package com.shop.server.utils.sql;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class BankCardSql {

    public static final String DELETE_SQL = """
            DELETE FROM bank_card
            WHERE id = ?
            """;

    public static final String SAVE_SQL = """
            INSERT INTO bank_card (users_id, card_number, expiry_date, bank, cvv, card_type)
            VALUES (?, ?, ?, ?, ?, ?)
            """;

    public static final String UPDATE_SQL = """
            UPDATE bank_card
            SET 
            users_id = ?,
            card_number = ?,
            expiry_date = ?,
            bank = ?,
            cvv = ?,
            card_type = ?
            WHERE id = ?
            """;

    public static final String FIND_ALL_SQL = """
            SELECT 
            id,
            users_id,
            card_number,
            expiry_date,
            bank,
            cvv,
            card_type
            FROM bank_card
            """;

    public static final String FIND_BY_ID_SQL = FIND_ALL_SQL + """
            WHERE id = ?
            """;
}
