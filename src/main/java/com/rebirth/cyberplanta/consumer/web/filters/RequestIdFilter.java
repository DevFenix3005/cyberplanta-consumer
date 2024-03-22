package com.rebirth.cyberplanta.consumer.web.filters;

import java.io.IOException;

import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Order(1)
@Component
@Slf4j
public class RequestIdFilter implements Filter {

    private static final String REQUEST_ID = "requestId";

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String requestId = req.getRequestId();
        MDC.put(REQUEST_ID, requestId);

        log.info("Starting a transaction for request: {}", req.getRequestURI());
        log.info("Request Id: {}", requestId);
        log.info("Method: {}", req.getMethod());

        chain.doFilter(request, response);

        log.info("Commiting a transaction for request: {}", req.getRequestURI());
        log.info("Logging Response ContentType:{}", res.getContentType());
        log.info("Logging Response Status:{}", res.getStatus());
        MDC.remove(REQUEST_ID);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
        MDC.remove(REQUEST_ID);
    }
}
