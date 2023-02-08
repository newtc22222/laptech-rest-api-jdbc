package com.laptech.restapi.mapper;

import com.laptech.restapi.model.ImportProduct;
import com.laptech.restapi.util.ConvertDateTime;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Nhat Phi
 * @since 2022-11-21
 */
public class ImportProductMapper implements RowMapper<ImportProduct> {
    @Override
    public ImportProduct mapRow(ResultSet rs, int rowNum) throws SQLException {
        ImportProduct importProduct = new ImportProduct();
        importProduct.setId(rs.getString("id"));
        importProduct.setProductId(rs.getString("product_id"));
        importProduct.setQuantity(rs.getLong("quantity"));
        importProduct.setImportedPrice(rs.getBigDecimal("imported_price"));
        importProduct.setImportedDate(ConvertDateTime.getDateTimeFromResultSet(rs, "imported_date"));
        importProduct.setCreatedDate(ConvertDateTime.getDateTimeFromResultSet(rs, "created_date"));
        importProduct.setModifiedDate(ConvertDateTime.getDateTimeFromResultSet(rs, "modified_date"));
        return importProduct;
    }
}