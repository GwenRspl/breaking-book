package com.projects.breakingbook.persistence.entity.mapper;

import com.projects.breakingbook.persistence.entity.Reader;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReaderMapper implements RowMapper<Reader> {
    @Override
    public Reader mapRow(ResultSet resultSet, int i) throws SQLException {
        return Reader.builder()
                .id(resultSet.getLong("reader_id"))
                .name(resultSet.getString("reader_name"))
                .avatar(resultSet.getString("reader_avatar"))
                .email(resultSet.getString("reader_email"))
                .password(resultSet.getString("reader_password"))
                .friends()
                .build();
    }
}
