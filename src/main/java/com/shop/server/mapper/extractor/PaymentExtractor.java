package com.shop.server.mapper.extractor;

import com.shop.server.dao.BankCardDao;
import com.shop.server.dao.OrderDao;
import com.shop.server.exception.DaoException;
import com.shop.server.model.entity.Payment;
import com.shop.server.model.type.ErrorResponseStatusType;
import com.shop.server.model.type.PaymentStatus;
import java.sql.ResultSet;
import java.sql.SQLException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PaymentExtractor implements EntityExtractor<Payment> {
    private static final PaymentExtractor INSTANCE = new PaymentExtractor();
    private final OrderDao orderDao = OrderDao.getInstance();
    private final BankCardDao bankCardDao = BankCardDao.getInstance();
    private final OrderExtractor orderExtractor = OrderExtractor.getExtractor();
    private final BankCardExtractor bankCardExtractor = BankCardExtractor.getExtractor();

    @Override
    public Payment extract(ResultSet resultSet) throws SQLException {
        return Payment.builder()
                .id(resultSet.getLong("id"))
                .order(orderDao.findById(resultSet.getLong("orders_id"),
                                resultSet.getStatement().getConnection(),
                                orderExtractor)
                        .orElseThrow(() -> new DaoException(ErrorResponseStatusType.DAO_EXCEPTION)))
                .bankCard(bankCardDao.findById(resultSet.getLong("bank_card_id"),
                                resultSet.getStatement().getConnection(),
                                bankCardExtractor)
                        .orElseThrow(() -> new DaoException(ErrorResponseStatusType.DAO_EXCEPTION)))
                .paymentTime(resultSet.getTimestamp("payment_time").toLocalDateTime())
                .paymentStatus(PaymentStatus.valueOf(resultSet.getString("payment_status")))
                .build();
    }

    public static PaymentExtractor getExtractor() {
        return INSTANCE;
    }
}
