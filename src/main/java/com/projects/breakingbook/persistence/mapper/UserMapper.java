package com.projects.breakingbook.persistence.mapper;

import com.projects.breakingbook.business.entity.User;
import com.projects.breakingbook.persistence.mapper.utils.MapperUtils;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper<User> {
    @Override
    public User mapRow(final ResultSet resultSet, final int i) throws SQLException {
        return MapperUtils.generateUserFromResultSet(resultSet);

    }
}
