package com.shop.server.model.entity;

import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
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
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Cart {

    @EqualsAndHashCode.Include
    private Long id;
    private List<ProductItem> productItems;
    private BigDecimal price;
    private PromoCode promoCode;

}


