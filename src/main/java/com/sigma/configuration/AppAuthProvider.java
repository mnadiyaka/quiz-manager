package com.sigma.configuration;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.sigma.JWTUtil;
import com.sigma.model.entity.User;
import com.sigma.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AppAuthProvider implements AuthenticationProvider {

    private final UserRepository userRepository;

    @Value("${jwt-settings.secret-key}")
    private String secret;

    @Value("${jwt-settings.issuer}")
    private String issuer;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String jwt = ((TokenAuth) authentication).getJwt();
        JWTUtil.verify(jwt, secret, issuer);

        DecodedJWT decode = JWT.decode(jwt);
        User user = userRepository.findById(Long.valueOf(decode.getSubject())).orElseThrow();

        ((TokenAuth) authentication).setUser(user);
        return authentication;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return TokenAuth.class.equals(authentication);
    }
}
