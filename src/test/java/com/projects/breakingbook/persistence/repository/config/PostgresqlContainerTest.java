package com.projects.breakingbook.persistence.repository.config;

import org.testcontainers.containers.PostgreSQLContainer;

public class PostgresqlContainerTest extends PostgreSQLContainer<PostgresqlContainerTest> {
    private static final String IMAGE_VERSION = "postgres:11.1";
    private static PostgresqlContainerTest container;

    private PostgresqlContainerTest() {
        super(IMAGE_VERSION);
    }

    public static PostgresqlContainerTest getInstance() {
        if (container == null) {
            container = new PostgresqlContainerTest();
        }
        return container;
    }

    @Override
    public void start() {
        super.start();
        System.setProperty("DB_URL", container.getJdbcUrl());
        System.setProperty("DB_USERNAME", container.getUsername());
        System.setProperty("DB_PASSWORD", container.getPassword());
    }

    @Override
    public void stop() {
    }
}

