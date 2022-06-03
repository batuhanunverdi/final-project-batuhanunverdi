package org.patikadev.finalprojectbatuhanunverdi.helper;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.util.Date;

/**
 * @author Mert Batuhan UNVERDI
 * @since 16.05.2022
 */
@Slf4j
@Component
@Getter
@Setter
public class JWTHelper {
    @Value("${fpb.jwt.secret-key}")
    private String secretKey;
    @Value("${fpb.jwt.expires-in}")
    private Long expiresIn;

    public String generate(String tc) {
        if (!StringUtils.hasLength(tc)) {
            throw new IllegalArgumentException("TC can not be null or empty");
        }
        return JWT.create().withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + expiresIn))
                .withClaim("username", tc)
                .sign(Algorithm.HMAC512(secretKey));
    }

    public String findByUsername(String token) {
        if (!StringUtils.hasLength(token)) {
            throw new IllegalArgumentException("TC can not be null or empty");
        }
        return JWT.decode(token).getClaim("username").asString();
    }

    public boolean validate(String token) {
        try {
            JWT.require(Algorithm.HMAC512(secretKey))
                    .build()
                    .verify(token);
            return true;
        } catch (AlgorithmMismatchException algorithmMismatchException) {
            log.error("JWT Token AlgorithmMismatchException occurred!");
        } catch (
                SignatureVerificationException signatureVerificationException) {
            log.error("JWT Token SignatureVerificationException occurred!");
        } catch (TokenExpiredException tokenExpiredException) {
            log.error("JWT Token TokenExpiredException occurred!");
        } catch (InvalidClaimException invalidClaimException) {
            log.error("JWT Token InvalidClaimException occurred!");
        }
        return false;
    }
}

