package com.schema.tenancy.impl;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class TenantConnectionProvider implements MultiTenantConnectionProvider {
    private static final long serialVersionUID = 1L;
    private static Logger logger = LoggerFactory.getLogger(TenantConnectionProvider.class);
    private transient DataSource datasource;

    public TenantConnectionProvider(DataSource dataSource) {
	this.datasource = dataSource;
    }

    @Override
    public Connection getAnyConnection() throws SQLException {
	return datasource.getConnection();
    }

    @Override
    public void releaseAnyConnection(Connection connection) throws SQLException {
	connection.close();
    }

    @Override
    public Connection getConnection(String tenantIdentifier) throws SQLException {
	logger.debug("Get connection for tenant {}", tenantIdentifier);
	final Connection connection = getAnyConnection();
	connection.setSchema(tenantIdentifier);
	return connection;
    }

    @Override
    public void releaseConnection(String tenantIdentifier, Connection connection) throws SQLException {
	logger.debug("Release connection for tenant {}", tenantIdentifier);
	connection.setSchema(TenantContext.DEFAULT_TENANT_ID);
	releaseAnyConnection(connection);
    }

    @Override
    public boolean supportsAggressiveRelease() {
	return false;
    }

    @Override
    @SuppressWarnings("rawtypes")
    public boolean isUnwrappableAs(Class aClass) {
	return false;
    }

    @Override
    public <T> T unwrap(Class<T> aClass) {
	return null;
    }
}