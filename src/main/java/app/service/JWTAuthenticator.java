package app.service;

import app.enums.Role;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.JWTClaimsSet;
import io.javalin.http.Context;
import io.javalin.http.ForbiddenResponse;
import io.javalin.http.UnauthorizedResponse;

import java.text.ParseException;

public class JWTAuthenticator {

    private final JWTService jwtService;

    public JWTAuthenticator(JWTService jwtService) {
        this.jwtService = jwtService;
    }

    public JWTClaimsSet authenticate(Context ctx) {
        String authorizationHeader = ctx.header("Authorization");

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new UnauthorizedResponse("Missing or invalid authorization header.");
        }

        String token = authorizationHeader.substring(7);

        if (token.isBlank()) {
            throw new UnauthorizedResponse("Missing JWT");
        }

        try {
            return jwtService.verifyToken(token);
        } catch (ParseException | JOSEException e) {
            throw new UnauthorizedResponse("JWT is invalid or expired.");
        }
    }

    public JWTClaimsSet requireRole(Context ctx, Role requiredRole) {
        JWTClaimsSet claims = authenticate(ctx);

        String userRole = (String) claims.getClaim("role");

        if (!requiredRole.name().equals(userRole)){
            throw new ForbiddenResponse("Permission denied.");
        }

        return claims;
    }

}
