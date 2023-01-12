package com.shop.server.service;

import com.shop.server.dao.PromoCodeDao;
import com.shop.server.exception.NotFoundException;
import com.shop.server.mapper.PromoCodeMapper;
import com.shop.server.mapper.extractor.PromoCodeExtractor;
import com.shop.server.model.dto.promo_code.PromoCodeDto;
import com.shop.server.model.dto.promo_code.PromoCodeRequest;
import com.shop.server.model.dto.promo_code.PromoCodeResponse;
import com.shop.server.model.dto.promo_code.PromoCodeUpdateRequest;
import com.shop.server.model.entity.PromoCode;
import com.shop.server.model.type.ErrorResponseStatusType;
import com.shop.server.utils.code_generator.PromoCodeGenerator;
import java.sql.Connection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PromoCodeService {

    private static final Logger log = LoggerFactory.getLogger(PromoCodeService.class);
    private static final PromoCodeService INSTANCE = new PromoCodeService();
    private final PromoCodeDao promoCodeDao = PromoCodeDao.getInstance();
    private final PromoCodeExtractor extractor = PromoCodeExtractor.getExtractor();
    private final PromoCodeMapper codeMapper = PromoCodeMapper.getInstance();

    public PromoCodeResponse save(PromoCodeRequest request, Connection connection) {
        log.info("[save] invoked with request = [{}]", request);
        PromoCode promoCode = codeMapper.toEntity(generatePromoCode(request));
        promoCodeDao.save(promoCode, connection);
        return codeMapper.toResponse(promoCode);
    }

    public PromoCodeResponse update(String key, PromoCodeUpdateRequest updateRequest) {
        log.info("[update] invoked with key = [{}] and updateRequest = [{}]", key, updateRequest);
        Optional<PromoCode> optionalPromoCode = promoCodeDao.findByKey(key, extractor);
        if (optionalPromoCode.isEmpty()) {
            throw new NotFoundException(ErrorResponseStatusType.PROMO_CODE_NOT_FOUND_EXCEPTION, key);
        }
        PromoCode promoCode = codeMapper.toEntity(updateRequest, optionalPromoCode.get());
        promoCodeDao.updateValue(promoCode);
        return codeMapper.toResponse(promoCode);
    }

    public List<PromoCodeResponse> getAll() {
        log.info("[getAll] invoked");
        List<PromoCode> promoCodes = promoCodeDao.findAll(extractor);
        if (promoCodes.isEmpty()) {
            throw new NotFoundException(ErrorResponseStatusType.PROMO_CODES_NOT_FOUND_EXCEPTION);
        }
        return promoCodes.stream()
                .map(codeMapper::toResponse)
                .collect(Collectors.toList());
    }

    public PromoCodeResponse getById(Long promoCodeId) {
        log.info("[getById] invoked with promoCodeId = [{}]", promoCodeId);
        Optional<PromoCode> optionalPromoCode = promoCodeDao.findById(promoCodeId, extractor);
        if (optionalPromoCode.isEmpty()) {
            throw new NotFoundException(ErrorResponseStatusType.PROMO_CODE_NOT_FOUND_EXCEPTION, promoCodeId);
        }
        return codeMapper.toResponse(optionalPromoCode.get());
    }

    public PromoCodeResponse getByKey(String promoCodeKey) {
        log.info("[getById] invoked with promoCodeKey = [{}]", promoCodeKey);
        Optional<PromoCode> optionalPromoCode = promoCodeDao.findByKey(promoCodeKey, extractor);
        if (optionalPromoCode.isEmpty()) {
            throw new NotFoundException(ErrorResponseStatusType.PROMO_CODE_NOT_FOUND_EXCEPTION, promoCodeKey);
        }
        return codeMapper.toResponse(optionalPromoCode.get());
    }

    public boolean delete(Long promoCodeId) {
        log.info("[delete] invoked with promoCodeId = [{}]", promoCodeId);
        Optional<PromoCode> optionalPromoCode = promoCodeDao.findById(promoCodeId, extractor);
        if (optionalPromoCode.isEmpty()) {
            throw new NotFoundException(ErrorResponseStatusType.PROMO_CODE_NOT_FOUND_EXCEPTION, promoCodeId);
        }
        return promoCodeDao.delete(promoCodeId);
    }

    public static PromoCodeService getInstance() {
        return INSTANCE;
    }

    private PromoCodeDto generatePromoCode(PromoCodeRequest request) {
        log.info("[generatePromoCode] invoked with request = [{}]", request);
        String key = PromoCodeGenerator.generate(request.getValue());
        while (promoCodeDao.findByKey(key, extractor).isPresent()) {
            key = PromoCodeGenerator.generate(request.getValue());
        }
        return codeMapper.toDto(key, request.getValue());
    }
}
