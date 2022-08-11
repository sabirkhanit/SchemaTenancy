package com.schema.tenancy.impl;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.hibernate.MultiTenancyStrategy;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

@Configuration
public class HibernateConfig {

    @Bean
    JpaVendorAdapter jpaVendorAdapter() {
	return new HibernateJpaVendorAdapter();
    }

    @Bean
    LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource, JpaProperties jpaProperties,
	    MultiTenantConnectionProvider multiTenantConnectionProviderImpl,
	    CurrentTenantIdentifierResolver currentTenantIdentifierResolverImpl) {

	Map<String, Object> jpaPropertiesMap = new HashMap<>(jpaProperties.getProperties());
	jpaPropertiesMap.put(AvailableSettings.MULTI_TENANT, MultiTenancyStrategy.SCHEMA);
	jpaPropertiesMap.put(AvailableSettings.MULTI_TENANT_CONNECTION_PROVIDER, multiTenantConnectionProviderImpl);
	jpaPropertiesMap.put(AvailableSettings.MULTI_TENANT_IDENTIFIER_RESOLVER, currentTenantIdentifierResolverImpl);

	LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
	em.setDataSource(dataSource);
	em.setPackagesToScan("com.schema.tenancy.*");
	em.setJpaVendorAdapter(this.jpaVendorAdapter());
	em.setJpaPropertyMap(jpaPropertiesMap);
	return em;
    }
}
