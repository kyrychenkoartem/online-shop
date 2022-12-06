package com.shop.server.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class DeliveryAddress {

    private Long id;
    private Order order;
    private String address;
    private String city;
    private String province;
    private String postalCode;
}
