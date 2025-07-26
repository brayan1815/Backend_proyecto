
package MODELO;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtils {
    // Método para encriptar la contraseña
    public static String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

    // Método para verificar la contraseña ingresada contra la guardada
    public static boolean checkPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
}
