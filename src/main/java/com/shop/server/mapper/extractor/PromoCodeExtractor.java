package com.shop.server.mapper.extractor;

import com.shop.server.model.entity.PromoCode;
import java.sql.ResultSet;
import java.sql.SQLException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PromoCodeExtractor implements EntityExtractor<PromoCode> {
    private static final PromoCodeExtractor INSTANCE = new PromoCodeExtractor();

    @Override
    public PromoCode extract(ResultSet resultSet) throws SQLException {
        return PromoCode.builder()
                .id(resultSet.getLong("id"))
                .key(resultSet.getString("key"))
                .value(resultSet.getInt("value"))
                .build();
    }

    public static PromoCodeExtractor getExtractor() {
        return INSTANCE;
    }
}
