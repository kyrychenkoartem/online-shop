package com.shop.server.service;

import com.shop.server.dao.CartDao;
import com.shop.server.dao.ProductItemDao;
import com.shop.server.exception.CartNotFoundException;
import com.shop.server.exception.ConnectionException;
import com.shop.server.exception.ProductItemNotFoundException;
import com.shop.server.exception.TransactionException;
import com.shop.server.mapper.CartMapper;
import com.shop.server.mapper.ProductItemMapper;
import com.shop.server.mapper.extractor.CartExtractor;
import com.shop.server.mapper.extractor.ProductItemExtractor;
import com.shop.server.model.dto.cart.CartResponse;
import com.shop.server.model.dto.product_item.ProductItemResponse;
import com.shop.server.model.entity.Cart;
import com.shop.server.model.entity.ProductItem;
import com.shop.server.model.type.ErrorResponseStatusType;
import com.shop.server.utils.ConnectionPool;
import com.shop.server.utils.TransactionManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import static java.util.stream.Collectors.toList;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CartService {

    private static final Logger log = LoggerFactory.getLogger(CartService.class);
    private static final CartService INSTANCE = new CartService();
    public final ProductService productService = ProductService.getInstance();
    public final ProductItemDao productItemDao = ProductItemDao.getInstance();
    private final CartDao cartDao = CartDao.getInstance();
    private final ProductItemExtractor itemExtractor = ProductItemExtractor.getExtractor();
    private final CartExtractor cartExtractor = CartExtractor.getExtractor();
    private final CartMapper cartMapper = CartMapper.getInstance();
    private final ProductItemMapper itemMapper = ProductItemMapper.getInstance();

    public List<CartResponse> getAll() {
        log.info("[getAll] invoked");
        List<Cart> carts = cartDao.findAll(cartExtractor);
        if (carts.isEmpty()) {
            throw new CartNotFoundException(ErrorResponseStatusType.CARTS_NOT_FOUND_EXCEPTION);
        }
        return carts.stream()
                .map(cartMapper::toResponse)
                .collect(toList());
    }

    public CartResponse findById(Long cartId) {
        log.info("[findById] invoked with cartId = [{}]", cartId);
        Optional<Cart> optionalCart = cartDao.findById(cartId, cartExtractor);
        if (optionalCart.isEmpty()) {
            throw new CartNotFoundException(ErrorResponseStatusType.CART_NOT_FOUND_EXCEPTION, cartId);
        }
        return cartMapper.toResponse(optionalCart.get());
    }

    public boolean delete(Long cartId) {
        log.info("[cartId] invoked with cartId [{}]", cartId);
        Optional<Cart> optionalCart = cartDao.findById(cartId, cartExtractor);
        if (optionalCart.isEmpty()) {
            throw new CartNotFoundException(ErrorResponseStatusType.CART_NOT_FOUND_EXCEPTION, cartId);
        }
        return cartDao.delete(cartId);
    }

    public CartResponse save(Long productId, Integer quantities) {
        log.info("[save] invoked with productId = [{}] and quantities = [{}]", productId, quantities);
        try (Connection connection = ConnectionPool.get()) {
            try {
                TransactionManager.startTransaction(connection);
                ProductItem productItem = productService.takeProduct(productId, quantities, connection);
                productItemDao.save(productItem, connection);
                ArrayList<ProductItemResponse> itemResponses = new ArrayList<>();
                itemResponses.add(itemMapper.toResponse(productItem));
                Cart cart = cartMapper.toEntity(productItem);
                cartDao.save(cart, connection);
                TransactionManager.commitTransaction(connection);
                return cartMapper.toResponse(itemResponses);
            } catch (TransactionException exception) {
                TransactionManager.rollbackTransaction(connection);
                throw new TransactionException(ErrorResponseStatusType.TRANSACTION_EXCEPTION, exception);
            } finally {
                TransactionManager.finishTransaction(connection);
            }
        } catch (ConnectionException | SQLException exception) {
            throw new ConnectionException(ErrorResponseStatusType.CONNECTION_EXCEPTION, exception);
        }
    }

    public CartResponse addToCart(Long cartId, Long productId, Integer quantities) {
        log.info("[addToCart] invoked with cartId = [{}] and productId = [{}] and quantities = [{}]", cartId, productId, quantities);
        try (Connection connection = ConnectionPool.get()) {
            try {
                TransactionManager.startTransaction(connection);
                Optional<Cart> optionalCart = cartDao.findById(cartId, connection, cartExtractor);
                if (optionalCart.isEmpty()) {
                    throw new CartNotFoundException(ErrorResponseStatusType.CART_NOT_FOUND_EXCEPTION, cartId);
                }
                ProductItem productItem = productService.takeProduct(productId, quantities, connection);
                if (isProductItemPresent(quantities, connection, optionalCart, productItem)) {
                    return cartMapper.toResponse(optionalCart.get());
                }
                productItemDao.save(productItem, cartId, connection);
                List<ProductItem> productItems = new ArrayList<>(optionalCart.get().getProductItems());
                productItems.add(productItem);
                optionalCart.get().setProductItems(productItems);
                cartDao.update(optionalCart.get(), connection);
                TransactionManager.commitTransaction(connection);
                return cartMapper.toResponse(optionalCart.get());
            } catch (TransactionException | IndexOutOfBoundsException exception) {
                TransactionManager.rollbackTransaction(connection);
                throw new TransactionException(ErrorResponseStatusType.TRANSACTION_EXCEPTION, exception);
            } finally {
                TransactionManager.finishTransaction(connection);
            }
        } catch (ConnectionException | SQLException exception) {
            throw new ConnectionException(ErrorResponseStatusType.CONNECTION_EXCEPTION, exception);
        }
    }

    public CartResponse deleteFromCart(Long cartId, Long productItemId) {
        log.info("[deleteFromCart] invoked with cartId = [{}] and productItemId = [{}]", cartId, productItemId);
        try (Connection connection = ConnectionPool.get()) {
            try {
                TransactionManager.startTransaction(connection);
                Optional<Cart> optionalCart = cartDao.findById(cartId, connection, cartExtractor);
                if (optionalCart.isEmpty()) {
                    throw new CartNotFoundException(ErrorResponseStatusType.CART_NOT_FOUND_EXCEPTION, cartId);
                }
                Optional<ProductItem> optionalProductItem = productItemDao.findById(productItemId, connection, itemExtractor);
                if (optionalProductItem.isEmpty()) {
                    throw new ProductItemNotFoundException(ErrorResponseStatusType.PRODUCT_ITEM_NOT_FOUND_EXCEPTION, productItemId);
                }
                productService.putProduct(optionalProductItem.get().getProduct().getId(),
                        optionalProductItem.get().getCount(), connection);
                List<ProductItem> productItems = new ArrayList<>(optionalCart.get().getProductItems());
                productItems.remove(optionalProductItem.get());
                optionalCart.get().setProductItems(productItems);
                cartDao.update(optionalCart.get(), connection);
                productItemDao.delete(productItemId, connection);
                TransactionManager.commitTransaction(connection);
                return cartMapper.toResponse(optionalCart.get());
            } catch (TransactionException exception) {
                TransactionManager.rollbackTransaction(connection);
                throw new TransactionException(ErrorResponseStatusType.TRANSACTION_EXCEPTION, exception);
            } finally {
                TransactionManager.finishTransaction(connection);
            }
        } catch (ConnectionException | SQLException exception) {
            throw new ConnectionException(ErrorResponseStatusType.CONNECTION_EXCEPTION, exception);
        }
    }

    public static CartService getInstance() {
        return INSTANCE;
    }

    private boolean isProductItemPresent(Integer quantities, Connection connection, Optional<Cart> optionalCart, ProductItem productItem) {
        if (optionalCart.get().getProductItems().stream().map(ProductItem::getProduct).anyMatch(product -> Objects.equals(product.getId(), productItem.getProduct().getId()))) {
            List<ProductItem> productItems = new ArrayList<>(optionalCart.get().getProductItems());
            ProductItem item = productItems.get(productItems.indexOf(productItem));
            item.setCount(item.getCount() + quantities);
            productItemDao.update(item, connection);
            optionalCart.get().setProductItems(productItems);
            cartDao.update(optionalCart.get(), connection);
            TransactionManager.commitTransaction(connection);
            return true;
        }
        return false;
    }
}
