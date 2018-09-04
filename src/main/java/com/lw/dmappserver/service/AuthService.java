package com.lw.dmappserver.service;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.TimeUnit;


public class AuthService {
    private Algorithm algorithm;
    private String secret;
    private JWTVerifier verifier;
    private static final Logger logger = LogManager.getLogger(AuthService.class);

    public AuthService(String secret) {
        this.secret = secret;
        this.algorithm = Algorithm.HMAC256(secret);
        this.verifier = JWT.require(algorithm)
                .withIssuer("lw-dm-apps")
                .acceptLeeway(5)
                .build();
    }

    public String sign(String userName) {
        try {
            return JWT.create()
                    .withIssuer("lw-dm-apps")
                    .withExpiresAt(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(30)))
                    .withIssuedAt(new Date(System.currentTimeMillis()))
                    .withClaim("user", userName)
                    .sign(algorithm);
        } catch (JWTCreationException e) {
            logger.warn("Unable to sign JWT");
            return null;
        }
    }

    public boolean verify(String requestToken) {
        try {
            DecodedJWT jwt = verifier.verify(requestToken);
            return true;
        } catch (JWTVerificationException exception){
            logger.warn("Unable to verify request JWT.\nToken: " + Arrays.toString(requestToken.split("\\.")));
            return false;
        }
    }

    // setters-getters
    public Algorithm getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(Algorithm algorithm) {
        this.algorithm = algorithm;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }


    public static void main(String [] args) {
        AuthService test = new AuthService("fruit");
        String token = test.sign("Praise");
        String badToken = JWT.create()
                .withIssuer("lw-dm-apps")
                .withExpiresAt(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(30)))
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withClaim("user", "Jubjub").sign(Algorithm.HMAC256("not the correct secret"));
        DecodedJWT jwt = JWT.decode(token);
        System.out.println(jwt.getHeader() + " :: " + jwt.getPayload() + " :: " + jwt.getSignature() + " :: " + jwt.getToken());
        System.out.println("Verifying token: " + test.verify(token));
        System.out.println("Verifying bad token: " + test.verify(badToken));
    }
}
