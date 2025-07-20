
package MODELO;

import MODELO.TokenUtils;
import com.auth0.jwt.exceptions.JWTVerificationException;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.core.Response;

@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class FiltroJWT implements ContainerRequestFilter {
    @Override
    public void filter(ContainerRequestContext requestContext) {
        String authHeader = requestContext.getHeaderString("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                    .entity("{\"error\":\"Token faltante o mal formado\"}").build());
            return;
        }

        String token = authHeader.substring("Bearer ".length());

        try {
            TokenUtils.verificarToken(token);
        } catch (JWTVerificationException e) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                    .entity("{\"error\":\"Token inválido o expirado\"}").build());
        }
    }
}

