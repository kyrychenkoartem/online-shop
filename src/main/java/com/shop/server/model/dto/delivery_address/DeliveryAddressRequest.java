package com.shop.server.model.dto.delivery_address;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeliveryAddressRequest {

    private String address;
    private String city;
    private String province;
    private String postalCode;
}
