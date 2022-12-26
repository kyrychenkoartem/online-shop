package com.shop.server.service;

import com.shop.server.dao.ProductDao;
import com.shop.server.exception.BadRequestException;
import com.shop.server.exception.NotFoundException;
import com.shop.server.exception.ProductNotEnoughException;
import com.shop.server.mapper.ProductItemMapper;
import com.shop.server.mapper.ProductMapper;
import com.shop.server.mapper.extractor.ProductExtractor;
import com.shop.server.model.dto.product.NewProductRequest;
import com.shop.server.model.dto.product.ProductFilter;
import com.shop.server.model.dto.product.ProductResponse;
import com.shop.server.model.dto.product.UpdateProductPriceRequest;
import com.shop.server.model.dto.product.UpdateProductQuantitiesRequest;
import com.shop.server.model.dto.product.UpdateProductRequest;
import com.shop.server.model.entity.Product;
import com.shop.server.model.entity.ProductItem;
import com.shop.server.model.type.ErrorResponseStatusType;
import java.sql.Connection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductService {

    private static final Logger log = LoggerFactory.getLogger(ProductService.class);
    private static final Integer LIMIT = 100;
    private static final Integer OFFSET = 0;
    private static final ProductService INSTANCE = new ProductService();
    private final ProductDao productDao = ProductDao.getInstance();
    private final ProductExtractor extractor = ProductExtractor.getExtractor();
    private final ProductMapper productMapper = ProductMapper.getInstance();
    private final ProductItemMapper productItemMapper = ProductItemMapper.getInstance();

    public ProductResponse getById(Long productId) {
        log.info("[getById] invoked with productId = [{}]", productId);
        Optional<Product> optionalProduct = productDao.findById(productId, extractor);
        if (optionalProduct.isEmpty()) {
            throw new NotFoundException(ErrorResponseStatusType.PRODUCT_NOT_FOUND_EXCEPTION, productId);
        }
        return productMapper.toResponse(optionalProduct.get());
    }

    public List<ProductResponse> getAll() {
        log.info("[getAll] invoked");
        List<Product> products = productDao.findAll(extractor);
        if (products.isEmpty()) {
            throw new NotFoundException(ErrorResponseStatusType.PRODUCTS_NOT_FOUND_EXCEPTION);
        }
        return products.stream()
                .map(productMapper::toResponse)
                .collect(Collectors.toList());
    }

    public List<ProductResponse> getAll(ProductFilter filter) {
        log.info("[getAll] invoked with filter = [{}]", filter);
        List<Product> products = productDao.findAll(filter, extractor);
        if (products.isEmpty()) {
            throw new NotFoundException(ErrorResponseStatusType.PRODUCTS_NOT_FOUND_EXCEPTION);
        }
        return products.stream()
                .map(productMapper::toResponse)
                .collect(Collectors.toList());
    }

    public ProductResponse save(NewProductRequest productRequest) {
        log.info("[save] invoked with productRequest = [{}]", productRequest);
        if (!checkAvailability(productRequest)) {
            log.error("[save] Product [{}] already exists", productRequest.getName());
            throw new BadRequestException(ErrorResponseStatusType.PRODUCT_ALREADY_EXISTS_EXCEPTION, productRequest.getName());
        }
        Product product = productMapper.toEntity(productRequest);
        productDao.save(product);
        return productMapper.toResponse(product);
    }

    public ProductResponse update(Long productId, UpdateProductRequest productRequest) {
        log.info("[update] invoked with productId = [{}] and productRequest = [{}]", productId, productRequest);
        Optional<Product> optionalProduct = productDao.findById(productId, extractor);
        if (optionalProduct.isEmpty()) {
            throw new NotFoundException(ErrorResponseStatusType.PRODUCT_NOT_FOUND_EXCEPTION, productId);
        }
        Product product = productMapper.toEntity(productRequest, optionalProduct.get());
        productDao.update(product);
        return productMapper.toResponse(product);
    }

    public boolean updatePrice(Long productId, UpdateProductPriceRequest productPriceRequest) {
        log.info("[updatePrice] invoked with productId = [{}] and productPriceRequest = [{}]", productId, productPriceRequest);
        Optional<Product> optionalProduct = productDao.findById(productId, extractor);
        if (optionalProduct.isEmpty()) {
            throw new NotFoundException(ErrorResponseStatusType.PRODUCT_NOT_FOUND_EXCEPTION, productId);
        }
        Product product = productMapper.toEntity(productPriceRequest, optionalProduct.get());
        return productDao.updatePrice(product);
    }

    public boolean updateQuantities(Long productId, UpdateProductQuantitiesRequest productQuantitiesRequest) {
        log.info("[updatePrice] invoked with productId = [{}] and productQuantitiesRequest = [{}]", productId, productQuantitiesRequest);
        Optional<Product> optionalProduct = productDao.findById(productId, extractor);
        if (optionalProduct.isEmpty()) {
            throw new NotFoundException(ErrorResponseStatusType.PRODUCT_NOT_FOUND_EXCEPTION, productId);
        }
        Product product = productMapper.toEntity(productQuantitiesRequest, optionalProduct.get());
        return productDao.updateQuantities(product);
    }

    public ProductItem takeProduct(Long productId, Integer quantities, Connection connection) {
        log.info("[takeProduct] invoked with productId = [{}], quantities = [{}] and connection = [{}]", productId, quantities, connection);
        Optional<Product> optionalProduct = productDao.findById(productId, extractor);
        if (optionalProduct.isEmpty()) {
            throw new NotFoundException(ErrorResponseStatusType.PRODUCT_NOT_FOUND_EXCEPTION, productId);
        }
        if (!productDao.isProductQuantitiesEnough(productId, quantities, connection)) {
            throw new ProductNotEnoughException(ErrorResponseStatusType.PRODUCT_NOT_ENOUGH_EXCEPTION, productId);
        }
        productDao.takeProduct(productId, quantities, connection);
        return productItemMapper.toEntity(optionalProduct.get(), quantities);
    }

    public boolean putProduct(Long productId, Integer quantities, Connection connection) {
        log.info("[putProduct] invoked with productId = [{}] and quantities = [{} ]and connection = [{}]", productId, quantities, connection);
        Optional<Product> optionalProduct = productDao.findById(productId, extractor);
        if (optionalProduct.isEmpty()) {
            throw new NotFoundException(ErrorResponseStatusType.PRODUCT_NOT_FOUND_EXCEPTION, productId);
        }
        return productDao.putProduct(productId, quantities, connection);
    }

    public boolean delete(Long productId) {
        log.info("[delete] invoked with productId = [{}]", productId);
        Optional<Product> optionalProduct = productDao.findById(productId, extractor);
        if (optionalProduct.isEmpty()) {
            throw new NotFoundException(ErrorResponseStatusType.PRODUCT_NOT_FOUND_EXCEPTION, productId);
        }
        return productDao.delete(productId);
    }

    public static ProductService getInstance() {
        return INSTANCE;
    }

    private boolean checkAvailability(NewProductRequest productRequest) {
        log.info("[checkAvailability] invoked with productRequest = [{}]", productRequest);
        ProductFilter filter = ProductFilter.builder().limit(LIMIT).offset(OFFSET).name(productRequest.getName())
                .price(productRequest.getPrice()).category(productRequest.getCategory()).material(productRequest.getMaterial()).build();
        List<Product> products = productDao.findAll(filter, extractor);
        if (products.isEmpty()) {
            return true;
        }
        return products.stream()
                .filter(product -> product.equals(productMapper.toEntity(productRequest)))
                .toList().isEmpty();
    }

}
