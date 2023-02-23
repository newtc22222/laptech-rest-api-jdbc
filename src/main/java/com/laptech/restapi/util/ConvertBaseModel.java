package com.laptech.restapi.util;

import com.laptech.restapi.model.BaseModel;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Nhat Phi
 * @since 2023-02-23
 */
public class ConvertBaseModel {
    public static BaseModel getBaseModel(ResultSet rs) throws SQLException {
        BaseModel model = new BaseModel();
        model.setCreatedDate(ConvertDateTime.getDateTimeFromResultSet(rs, "created_date"));
        model.setModifiedDate(ConvertDateTime.getDateTimeFromResultSet(rs, "modified_date"));
        model.setDeletedDate(ConvertDateTime.getDateTimeFromResultSet(rs, "deleted_date"));
        model.setDel(rs.getBoolean("is_del"));
        model.setUpdateBy(rs.getNString("update_by"));
        return model;
    }
}
