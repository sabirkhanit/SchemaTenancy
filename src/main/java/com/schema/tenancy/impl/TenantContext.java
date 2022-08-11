package com.schema.tenancy.impl;

public class TenantContext {

    public static final String HTTP_HEADER_TENANT_ID = "X-TenantID";
    public static final String DEFAULT_TENANT_ID = "default";
    /* Property Keys */
    public static final String PROPERTY_DB_DRIVER_CLASS_NAME = "spring.datasource.driver-class-name";
    public static final String PROPERTY_DB_URL = "spring.datasource.url";
    public static final String PROPERTY_DB_USER = "spring.datasource.username";
    public static final String PROPERTY_DB_PASS = "spring.datasource.password";
    public static final String PROPERTY_DB_SCHEMA = "spring.jpa.properties.hibernate.default_schema";

    private static ThreadLocal<String> currentTenant = new InheritableThreadLocal<>();

    public static String getCurrentTenant() {
	return currentTenant.get();
    }

    public static void setCurrentTenant(String tenant) {
	currentTenant.set(tenant);
    }

    public static void clear() {
	currentTenant.remove();
}

    private TenantContext() {

    }
}