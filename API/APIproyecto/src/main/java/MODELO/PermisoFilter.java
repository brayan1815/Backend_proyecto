/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODELO;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.io.IOException;
import java.lang.reflect.Method;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

@TienePermiso("") // importante para que se asocie al filtro
@Provider
public class PermisoFilter implements ContainerRequestFilter{
    @Context
    private ResourceInfo resourceInfo;

    private PermisosUtils permisosUtils = new PermisosUtils();

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        Method resourceMethod = resourceInfo.getResourceMethod();

        // Revisar si hay anotación en el método o en la clase
        TienePermiso tienePermiso = resourceMethod.getAnnotation(TienePermiso.class);
        if (tienePermiso == null) {
            tienePermiso = resourceInfo.getResourceClass().getAnnotation(TienePermiso.class);
        }

        // Si no hay anotación, no hacemos nada
        if (tienePermiso == null) {
            return;
        }

        String permisoNecesario = tienePermiso.value();

        // Obtener token del header Authorization
        String authHeader = requestContext.getHeaderString("Authorization");
//        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
//            requestContext.abortWith(
//                Response.status(Response.Status.UNAUTHORIZED)
//                        .entity("{\"error\":\"Token faltante o mal formado\"}")
//                        .build()
//            );
//            return;
//        }

        String token = authHeader.substring("Bearer ".length());

        try {
            // Decodificar token y obtener el correo (subject)
            DecodedJWT decoded = JWT.require(Algorithm.HMAC256(TokenUtils.SECRET))
                                    .build()
                                    .verify(token);
            String correo = decoded.getSubject();

            // Validar permiso con PermisosUtils
            if (!permisosUtils.tienePermiso(correo, permisoNecesario)) {
                requestContext.abortWith(
                    Response.status(Response.Status.FORBIDDEN)
                            .entity("{\"error\":\"No tiene permisos para acceder a este recurso\"}")
                            .build()
                );
            }
        } catch (Exception e) {
            requestContext.abortWith(
                Response.status(Response.Status.UNAUTHORIZED)
                        .entity("{\"error\":\"Token inválido o expirado\"}")
                        .build()
            );
        }
    }
}
