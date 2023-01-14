package com.shop.server.service;

import com.shop.server.dao.BankCardDao;
import com.shop.server.dao.DeliveryAddressDao;
import com.shop.server.dao.OrderDao;
import com.shop.server.exception.ConnectionException;
import com.shop.server.exception.NotFoundException;
import com.shop.server.exception.TransactionException;
import com.shop.server.exception.ValidationException;
import com.shop.server.mapper.BankCardMapper;
import com.shop.server.mapper.DeliveryAddressMapper;
import com.shop.server.mapper.extractor.BankCardExtractor;
import com.shop.server.mapper.extractor.OrderExtractor;
import com.shop.server.model.dto.bank_card.BankCardRequest;
import com.shop.server.model.dto.delivery_address.DeliveryAddressRequest;
import com.shop.server.model.type.ErrorResponseStatusType;
import com.shop.server.model.type.OrderStatus;
import com.shop.server.utils.ConnectionPool;
import com.shop.server.utils.TransactionManager;
import com.shop.server.validator.impl.BankCardRequestValidator;
import java.sql.Connection;
import java.sql.SQLException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PayService {

    private static final Logger log = LoggerFactory.getLogger(PayService.class);
    private final BankCardDao bankCardDao = BankCardDao.getInstance();
    private final OrderDao orderDao = OrderDao.getInstance();
    private final DeliveryAddressDao deliveryAddressDao = DeliveryAddressDao.getInstance();
    private final BankCardExtractor bankCardExtractor = BankCardExtractor.getExtractor();
    private final OrderExtractor orderExtractor = OrderExtractor.getExtractor();
    private final BankCardMapper bankCardMapper = BankCardMapper.getInstance();
    private final DeliveryAddressMapper addressMapper = DeliveryAddressMapper.getInstance();
    private final BankCardRequestValidator cardRequestValidator = BankCardRequestValidator.getInstance();

//    public BankCardResponse saveCard(BankCardRequest cardRequest, Long userId) {
//        log.info("[saveCard] invoked with cardRequest = [{}] and userId = [{}]", cardRequest, userId);
//        var validationResult = cardRequestValidator.isValid(cardRequest);
//        if (!validationResult.isValid()) {
//            throw new ValidationException(validationResult.getErrors());
//        }
//        var bankCard = bankCardMapper.toEntity(cardRequest);
//        var cardOptional = bankCardDao.findByUserId(bankCard, userId, bankCardExtractor);
//        if (cardOptional.isEmpty()) {
//            bankCardDao.save(bankCard, userId);
//        }
//        return bankCardMapper.toResponse(bankCard);
//    }
//
//    public DeliveryAddressResponse saveDeliveryAddress(DeliveryAddressRequest addressRequest, Long orderId) {
//        log.info("[saveDeliveryAddress] invoked with addressRequest = [{}] and orderId = [{}]", addressRequest, orderId);
//        var optionalOrder = orderDao.findById(orderId, orderExtractor);
//        if (optionalOrder.isEmpty()) {
//            throw new NotFoundException(ErrorResponseStatusType.ORDER_NOT_FOUND_EXCEPTION, orderId);
//        }
//        // TODO: 10.01.2023 fix addressRequest
//        var deliveryAddress = addressMapper.toEntity(addressRequest, optionalOrder.get());
//        deliveryAddressDao.save(deliveryAddress);
//        return addressMapper.toResponse(deliveryAddress);
//    }
//
//
//    public void finishPayment(Long orderId, DeliveryAddressResponse address, BankCardResponse card) {
//        try (Connection connection = ConnectionPool.get()) {
//            try {
//                TransactionManager.startTransaction(connection);
//                if (ObjectUtils.isEmpty(address) && ObjectUtils.isEmpty(card) && ObjectUtils.isEmpty(orderId)) {
//                    throw new NotFoundException(ErrorResponseStatusType.ARGUMENTS_NOT_FOUND_EXCEPTION);
//                }
//                var optionalOrder = orderDao.findById(orderId, connection, orderExtractor);
//                if (optionalOrder.isEmpty()) {
//                    throw new NotFoundException(ErrorResponseStatusType.ORDER_NOT_FOUND_EXCEPTION, orderId);
//                }
//                optionalOrder.get().setStatus(OrderStatus.COMPLETED);
//                orderDao.update(optionalOrder.get(), connection);
//                TransactionManager.commitTransaction(connection);
//            } catch (TransactionException | NotFoundException exception) {
//                TransactionManager.rollbackTransaction(connection);
//                throw new TransactionException(ErrorResponseStatusType.TRANSACTION_EXCEPTION, exception);
//            } finally {
//                TransactionManager.finishTransaction(connection);
//            }
//        } catch (ConnectionException | SQLException exception) {
//            throw new ConnectionException(ErrorResponseStatusType.CONNECTION_EXCEPTION, exception);
//        }
//    }

    public void finishPayment(Long orderId, Long userId, DeliveryAddressRequest addressRequest, BankCardRequest cardRequest) {
        log.info("[finishPayment] orderId = [{}] and userId = [{}] and addressRequest = [{}] and cardRequest = [{}]",
                orderId, userId, addressRequest, cardRequest);
        try (Connection connection = ConnectionPool.get()) {
            try {
                TransactionManager.startTransaction(connection);
                if (ObjectUtils.isEmpty(orderId) && ObjectUtils.isEmpty(userId)
                        && ObjectUtils.isEmpty(addressRequest) && ObjectUtils.isEmpty(cardRequest)) {
                    throw new NotFoundException(ErrorResponseStatusType.ARGUMENTS_NOT_FOUND_EXCEPTION);
                }
                // card save
                var validationResult = cardRequestValidator.isValid(cardRequest);
                if (!validationResult.isValid()) {
                    throw new ValidationException(validationResult.getErrors());
                }
                var bankCard = bankCardMapper.toEntity(cardRequest);
                var cardOptional = bankCardDao.findByUserId(bankCard, userId, connection, bankCardExtractor);
                if (cardOptional.isEmpty()) {
                    bankCardDao.save(bankCard, userId, connection);
                }
                // address delivery save
                var optionalOrder = orderDao.findById(orderId, connection, orderExtractor);
                if (optionalOrder.isEmpty()) {
                    throw new NotFoundException(ErrorResponseStatusType.ORDER_NOT_FOUND_EXCEPTION, orderId);
                }
                var deliveryAddress = addressMapper.toEntity(addressRequest, optionalOrder.get());
                deliveryAddressDao.save(deliveryAddress, connection);
                // order status change
                optionalOrder.get().setStatus(OrderStatus.COMPLETED);
                orderDao.update(optionalOrder.get(), connection);
                TransactionManager.commitTransaction(connection);
            } catch (TransactionException | NotFoundException exception) {
                TransactionManager.rollbackTransaction(connection);
                throw new TransactionException(ErrorResponseStatusType.TRANSACTION_EXCEPTION, exception);
            } finally {
                TransactionManager.finishTransaction(connection);
            }
        } catch (ConnectionException | SQLException exception) {
            throw new ConnectionException(ErrorResponseStatusType.CONNECTION_EXCEPTION, exception);
        }
    }

    private static final PayService INSTANCE = new PayService();

    public static PayService getInstance() {
        return INSTANCE;
    }

}
