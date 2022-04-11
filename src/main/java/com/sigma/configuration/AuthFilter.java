package com.sigma.configuration;

import com.sigma.JWTUtil;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;

@Slf4j
@Component
public class AuthFilter extends OncePerRequestFilter {

    @Value("${jwt-settings.secret-key}")
    private String secret;

    @Value("${jwt-settings.issuer}")
    private String issuer;

    private final String AUTH = "Authorization";

    private List<String> publicUrls = Arrays.asList("login", "signUp");

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String jwt = ofNullable(request.getHeader(AUTH)).orElseThrow(() -> new NoSuchElementException());
        JWTUtil.verify(jwt, secret, issuer);
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return (request.getRequestURI()).matches(publicUrls.stream().map(pu -> String.format("(/%s)", pu)).collect(Collectors.joining("|")));
    }

    @Bean // TODO: move to Application.class?
    public FilterRegistrationBean<AuthFilter> someFilter() {
        final FilterRegistrationBean<AuthFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(this);
        registrationBean.addUrlPatterns("/*");
        registrationBean.setName("AuthFilter");
        registrationBean.setOrder(Integer.MAX_VALUE);
        return registrationBean;
    }
}