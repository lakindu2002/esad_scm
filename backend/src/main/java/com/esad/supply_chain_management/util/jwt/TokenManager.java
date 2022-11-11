package com.esad.supply_chain_management.util.jwt;

import com.esad.supply_chain_management.exceptions.ResourceInvalidException;
import org.jose4j.jwa.AlgorithmConstraints;
import org.jose4j.jwk.RsaJsonWebKey;
import org.jose4j.jwk.RsaJwkGenerator;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.lang.JoseException;

/**
 * Singleton token manager to help create, decode and verify JWT Tokens.
 */
public class TokenManager {

    // the single instance that will be reused
    private static TokenManager tokenManager;

    // static initialization block that creates an instance of the JWT Provider as an eager initialization.
    static {
        try {
            tokenManager = new TokenManager();
        } catch (JoseException e) {
            e.printStackTrace();
        }
    }

    // a web key for private/public key management
    private final RsaJsonWebKey rsaJsonWebKey;

    // private constructor to prevent external initialization
    private TokenManager() throws JoseException {
        // generate a key when instance of TokenManager is created
        this.rsaJsonWebKey = RsaJwkGenerator.generateJwk(2048);
    }

    // return the singleton static instance.
    public static TokenManager getTokenManager() {
        return tokenManager;
    }

    // create a token based on the email passed.
    public String createToken(String email) throws JoseException {
        rsaJsonWebKey.setKeyId("key01");

        JwtClaims claims = new JwtClaims();
        claims.setClaim("email", email); // set email claim
        claims.setClaim("role", "ADMINISTRATOR"); //set role claim
        claims.setSubject("ADMIN_TOKEN"); // set subject of token
        claims.setIssuer("SCM"); // set issuer
        claims.setAudience("ADMIN"); // set target audience
        claims.setIssuedAtToNow(); // add current time as issued at
        claims.setExpirationTimeMinutesInTheFuture(30); // 30 min expiry
        claims.setGeneratedJwtId(); // set JWT id

        // sign token
        JsonWebSignature signature = new JsonWebSignature();
        signature.setPayload(claims.toJson());
        // sign using private key
        signature.setKey(rsaJsonWebKey.getPrivateKey());

        // set algorithm
        signature.setAlgorithmHeaderValue(AlgorithmIdentifiers.RSA_USING_SHA256);

        // compile jwt and return jwt.
        return signature.getCompactSerialization();
    }

    public JwtClaims decodeToken(String token) throws ResourceInvalidException {
        JwtConsumer jwtConsumer = new JwtConsumerBuilder()
                .setRequireExpirationTime() // the JWT must have an expiration time
                .setAllowedClockSkewInSeconds(30) // allow some leeway in validating time based claims to account for clock skew
                .setRequireSubject() // the JWT must have a subject claim
                .setExpectedIssuer("SCM") // whom the JWT needs to have been issued by as defined above
                .setExpectedAudience("ADMIN") // to whom the JWT is intended for as defined above
                .setExpectedSubject("ADMIN_TOKEN") // as defined above
                .setVerificationKey(rsaJsonWebKey.getKey()) // verify the signature with the public key
                .setJwsAlgorithmConstraints( // only allow the expected signature algorithm(s) in the given context
                        AlgorithmConstraints.ConstraintType.PERMIT, AlgorithmIdentifiers.RSA_USING_SHA256) // which is only RS256 here
                .build(); // create
        try {
            //  Validate the JWT and process it to the Claims
            return jwtConsumer.processToClaims(token);
        } catch (InvalidJwtException e) {
            // InvalidJwtException will be thrown, if the JWT failed processing or validation in anyway.
            // Hopefully with meaningful explanations(s) about what went wrong.

            // Whether the JWT has expired being one common reason for invalidity
            if (e.hasExpired()) {
                throw new ResourceInvalidException("Token has expired");
            }
            throw new ResourceInvalidException("Token tampered");
        }
    }
}
