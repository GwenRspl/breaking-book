package com.projects.breakingbook.persistence.mapper;

import com.projects.breakingbook.business.entity.Collection;
import com.projects.breakingbook.business.entity.User;
import com.projects.breakingbook.utils.MapperUtils;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class CollectionMapper implements RowMapper<Collection> {
    @Override
    public Collection mapRow(final ResultSet resultSet, final int i) throws SQLException {
        final User user = MapperUtils.generateUserFromResultSet(resultSet);

        return Collection.builder()
                .id(resultSet.getLong("collection_id"))
                .name(resultSet.getString("collection_name"))
                .user(user)
                .build();
    }
}
