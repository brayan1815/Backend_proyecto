
package CONTROLADOR;

import MODELO.TokenUtils;
import com.auth0.jwt.JWT;
import java.io.StringReader;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/refreshToken") //ruta base del endpoint refreshToken
public class RefreshTokenController {
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response refreshToken(String body) {
        try {
            // Parsear el refreshToken desde el JSON recibido
            JsonObject json = Json.createReader(new StringReader(body)).readObject();
            String refreshToken = json.getString("refreshToken");

            // Verificar el refreshToken
            TokenUtils.verificarRefreshToken(refreshToken);

            // Obtener el correo del usuario desde el refreshToken
            String correo = JWT.decode(refreshToken).getSubject();

            // Generar un nuevo access token
            String newToken = TokenUtils.generarToken(correo);

            String responseJson = "{ \"token\": \"" + newToken + "\" }";
            return Response.ok(responseJson).build();

        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED)
                           .entity("{\"error\":\"Refresh token inválido o expirado\"}")
                           .build();
        }
    }
}
