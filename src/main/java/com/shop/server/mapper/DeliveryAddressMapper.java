package com.shop.server.mapper;

import com.shop.server.exception.NotFoundException;
import com.shop.server.model.dto.delivery_address.DeliveryAddressRequest;
import com.shop.server.model.dto.delivery_address.DeliveryAddressResponse;
import com.shop.server.model.entity.DeliveryAddress;
import com.shop.server.model.entity.Order;
import com.shop.server.model.type.ErrorResponseStatusType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DeliveryAddressMapper {

    private static final DeliveryAddressMapper INSTANCE = new DeliveryAddressMapper();

    public DeliveryAddress toEntity(DeliveryAddressRequest request, Order order) {
        if (ObjectUtils.isEmpty(request) && ObjectUtils.isEmpty(order)) {
            throw new NotFoundException(ErrorResponseStatusType.ARGUMENTS_NOT_FOUND_EXCEPTION);
        }
        return DeliveryAddress.builder()
                .order(order)
                .address(request.getAddress())
                .city(request.getCity())
                .province(request.getProvince())
                .postalCode(request.getPostalCode())
                .build();
    }

    public DeliveryAddressResponse toResponse(DeliveryAddress deliveryAddress) {
        if (ObjectUtils.isEmpty(deliveryAddress)) {
            throw new NotFoundException(ErrorResponseStatusType.ARGUMENTS_NOT_FOUND_EXCEPTION, deliveryAddress);
        }
        return DeliveryAddressResponse.builder()
                .order(deliveryAddress.getOrder())
                .address(deliveryAddress.getAddress())
                .city(deliveryAddress.getCity())
                .province(deliveryAddress.getProvince())
                .postal_code(deliveryAddress.getPostalCode())
                .build();
    }

    public static DeliveryAddressMapper getInstance() {
        return INSTANCE;
    }
}