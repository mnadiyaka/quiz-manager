package com.sigma;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.sigma.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;

import java.util.Base64;
import java.util.Date;

@Slf4j
@RequiredArgsConstructor
public class JWTUtil {

    private final AuthenticationManager authenticationManager;

    public static String generateJWT(User user, String issuer) {
        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes()); //TODO: put in file
        String access_token = JWT.create()
                .withSubject(user.getUsername())
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withExpiresAt(new Date(System.currentTimeMillis() + 5 * 60 * 1000)) //TODO: change time
                .sign(algorithm);

        return access_token;
    }

    public static void verify(User user, final String token) {

        String[] chunks = token.split("\\.");

        Base64.Decoder decoder = Base64.getUrlDecoder();

        String header = new String(decoder.decode(chunks[0]));
        String payload = new String(decoder.decode(chunks[1]));

        log.info("user {}, password {}", user.getUsername(), user.getPassword());
    }
}
