package app.service;

import app.entities.User;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;

import java.text.ParseException;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;

public class JWTService {

    private final byte[] secret;

    public JWTService(String encodedSecret) {
        this.secret = Base64.getDecoder().decode(encodedSecret);

        if (secret.length < 32) {
            throw new IllegalArgumentException("JWT secret must be at least 32 bytes");
        }
    }

    public String generateToken(User user) throws JOSEException {
        JWTClaimsSet claims = new JWTClaimsSet.Builder()
                .subject(String.valueOf(user.getId()))
                .claim("role", user.getRole().name())
                .issueTime(new Date())
                .expirationTime(Date.from(Instant.now().plusSeconds(3600)))
                .build();

        JWSObject jwsObject = new JWSObject(
                new JWSHeader(JWSAlgorithm.HS256),
                new Payload(claims.toJSONObject())
        );

        JWSSigner signer = new MACSigner(secret);
        jwsObject.sign(signer);

        return jwsObject.serialize();
    }

    public JWTClaimsSet verifyToken(String token) throws ParseException, JOSEException {

        JWSObject jwsObject = JWSObject.parse(token);

        if (!JWSAlgorithm.HS256.equals(jwsObject.getHeader().getAlgorithm())) {
            throw new JOSEException("Invalid JWT algorithm");
        }

        JWSVerifier verifier = new MACVerifier(secret);

        if (!jwsObject.verify(verifier)) {
            throw new JOSEException("Invalid JWT signature");
        }

        JWTClaimsSet claims = JWTClaimsSet.parse(jwsObject.getPayload().toJSONObject());

        if (claims.getExpirationTime() == null || !claims.getExpirationTime().after(new Date())) {
            throw new JOSEException("JWT has expired.");
        }

        return claims;
    }

}
