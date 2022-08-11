package com.schema.tenancy.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.schema.tenancy.impl.TenantContext;



@Component
public class RequestInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(RequestInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
	logger.info("In preHandle we are Intercepting the Request");
	logger.info("____________________________________________");
	String requestURI = request.getRequestURI();
	String tenantID = request.getHeader(TenantContext.HTTP_HEADER_TENANT_ID);
	logger.info("RequestURI:: {} || Search for X-TenantID  :: {} ", requestURI, tenantID);
	logger.info("____________________________________________");
	if (StringUtils.isEmpty(tenantID)) {
	    tenantID = TenantContext.DEFAULT_TENANT_ID;
	}
	TenantContext.setCurrentTenant(tenantID);
	logger.info("Final calculated value for tenantID is - {} ", tenantID);
	return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
	    ModelAndView modelAndView) throws Exception {
	TenantContext.clear();
    }

}
