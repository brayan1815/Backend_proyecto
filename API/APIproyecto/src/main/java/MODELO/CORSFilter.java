package MODELO;

import java.io.IOException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

@Provider
public class CORSFilter implements ContainerResponseFilter {
    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
            throws IOException {

        // se permite un origen especifico, en este caso http://localhost:5174
        responseContext.getHeaders().putSingle("Access-Control-Allow-Origin", "http://localhost:5173");
        //indice que cabeceras personalizadas puede incluir el cleinte en sus solicitufes
        responseContext.getHeaders().putSingle("Access-Control-Allow-Headers", "origin, content-type, accept, authorization");
        //Especifica qué métodos HTTP se permiten al cliente cuando hace peticiones CORS
        responseContext.getHeaders().putSingle("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
        // Indica que las cookies, cabeceras de autorización u otras credenciales pueden incluirse en la solicitud
        responseContext.getHeaders().putSingle("Access-Control-Allow-Credentials", "true");
    }
}
