package com.sigma;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.sigma.model.entity.User;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Slf4j
public class JWTUtil {

    public static String generateJWT(User user, String secret, int timestamp, String issuer) {

        Algorithm algorithm = Algorithm.HMAC256(secret.getBytes());
        final String accessToken = JWT.create()
                .withSubject(user.getId().toString())
                .withClaim("role", user.getRole().toString())
                .withIssuer(issuer)
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withExpiresAt(new Date(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(timestamp)))
                .sign(algorithm);

        return accessToken;
    }

    public static void verify(final String token, String secret, String issuer) {
        Algorithm algorithm = Algorithm.HMAC256(secret.getBytes());
        log.info(token);
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer(issuer)
                .build();


        verifier.verify(token.substring(7));

        DecodedJWT jwt = JWT.decode(token.substring(7));
        if (jwt.getExpiresAt().before(new Date())) {
            log.error("Token expired");
            throw new TokenExpiredException("Token expired: exists until " + jwt.getExpiresAt() + " now is " + System.currentTimeMillis());
        }
    }
}
