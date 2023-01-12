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

    public static final String FIND_BY_USER_ID_SQL = """
            SELECT 
            bc.id,
            bc.users_id,
            bc.card_number,
            bc.expiry_date,
            bc.bank,
            bc.cvv,
            bc.card_type
            FROM bank_card bc 
            JOIN users u on u.id = bc.users_id
            WHERE bc.users_id = ?
            AND bc.card_number = ?
            AND bc.expiry_date = ?
            AND bc.bank = ?
            AND bc.cvv = ?
            AND bc.card_type = ?
            """;
}
