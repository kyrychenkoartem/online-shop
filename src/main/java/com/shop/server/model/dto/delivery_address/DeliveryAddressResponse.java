package com.shop.server.model.dto.delivery_address;

import com.shop.server.model.entity.Order;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class DeliveryAddressResponse {

    private Order order;
    private String address;
    private String city;
    private String province;
    private String postal_code;
}
