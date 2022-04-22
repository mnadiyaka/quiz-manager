package com.sigma.configuration;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.sigma.JWTUtil;
import com.sigma.model.entity.User;
import com.sigma.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
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
import static org.springframework.util.ObjectUtils.isEmpty;

@Slf4j
@Component
public class AuthFilter extends OncePerRequestFilter {

    @Value("${jwt-settings.secret-key}")
    private String secret;

    @Value("${jwt-settings.issuer}")
    private String issuer;

    @Autowired
    private UserRepository userRepository;

    private final String AUTH = "Authorization";

    private List<String> publicUrls = Arrays.asList("login", "signUp");

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
//        if (isEmpty(header) || !header.startsWith("Bearer ")) {
//            filterChain.doFilter(request, response);
//            return;
//        }

        final String token = ofNullable(request.getHeader(HttpHeaders.AUTHORIZATION).substring(7)).orElseThrow(() -> new NoSuchElementException());

//        JWTUtil.verify(token, secret, issuer);

        final TokenAuth tokenAuth = new TokenAuth(token);

        SecurityContextHolder.getContext().setAuthentication(tokenAuth);

        filterChain.doFilter(request, response);




//        DecodedJWT jwt = JWT.decode(token.substring(7));
//        User user = userRepository
//                .findById(Long.valueOf((jwt.getSubject()))).orElseThrow(() -> new NoSuchElementException());
//        UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), AuthorityUtils.createAuthorityList(user.getRole().toString()));
//
//        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken
//                (userDetails, null, userDetails == null ? List.of() : userDetails.getAuthorities());
//
//        authentication.setDetails(
//                new WebAuthenticationDetailsSource().buildDetails(request)
//        );
//
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return request.getRequestURI().matches("/login");
//        return (request.getRequestURI()).matches(publicUrls.stream().map(pu -> String.format("(/%s)", pu)).collect(Collectors.joining("|")));
    }

}