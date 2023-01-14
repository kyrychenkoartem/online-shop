package com.shop.server.mapper;

import com.shop.server.exception.NotFoundException;
import com.shop.server.model.dto.promo_code.PromoCodeDto;
import com.shop.server.model.dto.promo_code.PromoCodeResponse;
import com.shop.server.model.dto.promo_code.PromoCodeUpdateRequest;
import com.shop.server.model.entity.PromoCode;
import com.shop.server.model.type.ErrorResponseStatusType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PromoCodeMapper {

    private static final PromoCodeMapper INSTANCE = new PromoCodeMapper();

    public PromoCodeDto toDto(String key, Integer value) {
        if (ObjectUtils.isEmpty(key) && ObjectUtils.isEmpty(value)) {
            throw new NotFoundException(ErrorResponseStatusType.ARGUMENTS_NOT_FOUND_EXCEPTION);
        }
        return PromoCodeDto.builder()
                .key(key)
                .value(value)
                .build();
    }

    public PromoCode toEntity(PromoCodeDto dto) {
        if (ObjectUtils.isEmpty(dto)) {
            throw new NotFoundException(ErrorResponseStatusType.ARGUMENT_NOT_FOUND_EXCEPTION, dto);
        }
        return PromoCode.builder()
                .key(dto.getKey())
                .value(dto.getValue())
                .build();
    }

    public PromoCode toEntity(PromoCodeUpdateRequest request, PromoCode promoCode) {
        if (ObjectUtils.isEmpty(request) && ObjectUtils.isEmpty(promoCode)) {
            throw new NotFoundException(ErrorResponseStatusType.ARGUMENTS_NOT_FOUND_EXCEPTION);
        }
        promoCode.setValue(request.getValue());
        return promoCode;
    }

    public PromoCodeResponse toResponse(PromoCode entity) {
        if (ObjectUtils.isEmpty(entity)) {
            throw new NotFoundException(ErrorResponseStatusType.ARGUMENT_NOT_FOUND_EXCEPTION, entity);
        }
        return PromoCodeResponse.builder()
                .key(entity.getKey())
                .value(entity.getValue())
                .build();
    }

    public static PromoCodeMapper getInstance() {
        return INSTANCE;
    }
}
