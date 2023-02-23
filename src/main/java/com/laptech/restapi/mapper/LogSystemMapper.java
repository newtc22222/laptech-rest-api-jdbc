package com.laptech.restapi.mapper;

import com.laptech.restapi.model.LogSystem;
import com.laptech.restapi.util.ConvertDateTime;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LogSystemMapper implements RowMapper<LogSystem> {
    @Override
    public LogSystem mapRow(ResultSet rs, int rowNum) throws SQLException {
        LogSystem log = new LogSystem();
        log.setId(rs.getLong("id"));
        log.setActionTable(rs.getString("action_table"));
        log.setActionTime(ConvertDateTime.getDateTimeFromResultSet(rs, "action_time"));
        log.setActionBy(rs.getNString("action_by"));
        log.setActionName(rs.getString("action_name"));
        return log;
    }
}
