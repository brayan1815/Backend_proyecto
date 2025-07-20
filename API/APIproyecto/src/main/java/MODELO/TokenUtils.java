
package MODELO;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;

import java.util.Date;

public class TokenUtils {
    private static final String SECRET = "clave123";

    public static String generarToken(String correo) {
        return JWT.create()
                .withSubject(correo)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + 3600000)) // 1 hora
                .sign(Algorithm.HMAC256(SECRET));
    }

    public static void verificarToken(String token) throws JWTVerificationException {
        JWT.require(Algorithm.HMAC256(SECRET)).build().verify(token);
    }
}
