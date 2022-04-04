package com.sigma;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.sigma.model.User;

import java.util.Date;

public class JWTUtil {

    public static String generateJWT(User user, String secret, int timestamp, String issuer) {

        Algorithm algorithm = Algorithm.HMAC256(secret.getBytes());
        final String access_token = JWT.create()
                .withSubject(user.getId().toString())
                .withClaim("role", user.getRole().toString())
                .withIssuer(issuer)
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withExpiresAt(new Date(System.currentTimeMillis() + timestamp))
                .sign(algorithm);

        return access_token;
    }

    public static void verify(final String token, String secret, String issuer) {
        Algorithm algorithm = Algorithm.HMAC256(secret.getBytes());
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer("http://localhost:8080/")
                .build();

        verifier.verify(token);
    }
}
