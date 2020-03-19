package com.example.demo.filiter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(value = Ordered.HIGHEST_PRECEDENCE)
public class CustomFilters implements Filter {
	public CustomFilters() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request1 = (HttpServletRequest) request;
		HttpServletResponse response1 = (HttpServletResponse) response;
		response1.setHeader("Access-Control-Allow-Origin", "*");
		response1.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTONS, DELETE");
		response1.setHeader("Access-Control-Max-Age", "3600");
		response1.setHeader("Access-Control-Allow-Headers", "x-requested-with, authorization");
		if ("OPTIONS".equalsIgnoreCase(request1.getMethod())) {
			response1.setStatus(HttpServletResponse.SC_OK);
		} else {
			chain.doFilter(request1, response1);
		}

	}

	@Override
	public void init(FilterConfig config) {

	}

	@Override
	public void destroy() {

	}
}
