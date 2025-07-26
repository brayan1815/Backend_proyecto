
package MODELO;

import MODELO.TokenUtils;
import com.auth0.jwt.exceptions.JWTVerificationException;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.core.Response;

@Secured // Anotación personalizada que indica que este filtro se aplica a métodos o clases con esta anotación
@Provider// Registra esta clase como un filtro activo en la aplicación
@Priority(Priorities.AUTHENTICATION)
public class FiltroJWT implements ContainerRequestFilter {
    @Override
    public void filter(ContainerRequestContext requestContext) {
        // Obtiene el valor del encabezado Authorization de la petición HTTP
        String authHeader = requestContext.getHeaderString("Authorization");
        
        // Si no hay encabezado o no comienza con "Bearer ", se rechaza la petición
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                    .entity("{\"error\":\"Token faltante o mal formado\"}").build());
            return;//se detiene el filtro aqui
        }
        
        // Extrae el token quitando el prefijo "Bearer "
        String token = authHeader.substring("Bearer ".length());

        try {
            // Verifica que el token sea válido usando la clase TokenUtils
            TokenUtils.verificarToken(token);
        } catch (JWTVerificationException e) {
            // Si el token es inválido o expiró, se rechaza la petición con código 401
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                    .entity("{\"error\":\"Token inválido o expirado\"}").build());
        }
    }
}

