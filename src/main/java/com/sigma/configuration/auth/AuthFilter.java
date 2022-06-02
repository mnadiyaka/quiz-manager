package com.sigma.configuration.auth;

import lombok.extern.slf4j.Slf4j;

import org.springframework.security.core.context.SecurityContextHolder;
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
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Component
public class AuthFilter extends OncePerRequestFilter {

    private final String AUTH = "Authorization";

    private final List<String> publicUrls = Arrays.asList("login", "signUp");

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final String token = Optional.of(request.getHeader(AUTH).substring(7)).orElseThrow(NoSuchElementException::new);

        final TokenAuth tokenAuth = new TokenAuth(token);

        SecurityContextHolder.getContext().setAuthentication(tokenAuth);

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return (request.getRequestURI()).matches(publicUrls.stream().map(pu -> String.format("(/%s)", pu)).collect(Collectors.joining("|")));
    }
}