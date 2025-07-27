
package MODELO;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;

import java.util.Date;

public class TokenUtils {
    //clave secreta para verificar y firmar los tokens
    private static final String SECRET = "clave123";
    
    //metodo para generar el token con base en el correo
    public static String generarToken(String correo) {
        return JWT.create()//crea un nuevo token
                .withSubject(correo)//asigan el sujeto del token
                .withIssuedAt(new Date())//fecha de creacion del token
                .withExpiresAt(new Date(System.currentTimeMillis() + 10*60*1000)) //fecha de expiracion(10 minutos desde ahora)
                .sign(Algorithm.HMAC256(SECRET));//firma el token con la palabra secreta
    }
    //metodo para verificar si el token es valido
    public static void verificarToken(String token) throws JWTVerificationException {
        //se verifica que el token tenga la misma clave secreta y sea el mismo
        JWT.require(Algorithm.HMAC256(SECRET)).build().verify(token);
    }
}
