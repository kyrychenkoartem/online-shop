package com.shop.server.mapper.extractor;

import com.shop.server.dao.UserDao;
import com.shop.server.exception.DaoException;
import com.shop.server.model.entity.BankCard;
import com.shop.server.model.type.CardType;
import com.shop.server.model.type.ErrorResponseStatusType;
import java.sql.ResultSet;
import java.sql.SQLException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BankCardExtractor implements EntityExtractor<BankCard> {

    private static final BankCardExtractor INSTANCE = new BankCardExtractor();
    private final UserDao userDao = UserDao.getInstance();
    private final UserExtractor extractor = UserExtractor.getExtractor();

    @Override
    public BankCard extract(ResultSet resultSet) throws SQLException {
        return BankCard.builder()
                .id(resultSet.getLong("id"))
                .user(userDao.findById(resultSet.getLong("users_id"),
                                resultSet.getStatement().getConnection(),
                                extractor)
                        .orElseThrow(() -> new DaoException(ErrorResponseStatusType.DAO_EXCEPTION)))
                .cardNumber(resultSet.getString("card_number"))
                .expiryDate(resultSet.getString("expiry_date"))
                .bank(resultSet.getString("bank"))
                .cvv(resultSet.getString("cvv"))
                .cardType(CardType.valueOf(resultSet.getString("card_type")))
                .build();
    }

    public static BankCardExtractor getExtractor() {
        return INSTANCE;
    }
}
