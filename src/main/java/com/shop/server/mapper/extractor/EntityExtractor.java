package com.shop.server.mapper.extractor;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface EntityExtractor<E> {

    E extract(ResultSet resultSet) throws SQLException;
}
