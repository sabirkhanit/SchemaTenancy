package com.schema.tenancy.impl;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.stereotype.Component;

@Component
public class TenantSchemaResolver implements CurrentTenantIdentifierResolver {

    @Override
    public String resolveCurrentTenantIdentifier() {
	String t = TenantContext.getCurrentTenant();
	if (t != null) {
	    return t;
	} else {
	    return TenantContext.DEFAULT_TENANT_ID;
	}
    }

    @Override
    public boolean validateExistingCurrentSessions() {
	return true;
    }
}
