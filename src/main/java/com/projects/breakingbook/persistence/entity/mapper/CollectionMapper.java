package com.projects.breakingbook.persistence.entity.mapper;

import com.projects.breakingbook.persistence.entity.Collection;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class CollectionMapper implements RowMapper<Collection> {
    @Override
    public Collection mapRow(ResultSet resultSet, int i) throws SQLException {
                        return Collection.builder()
                                .id(resultSet.getLong("collection_id"))
                                .name(resultSet.getString("collection_name"))
                                .build();
    }
}
