
package MODELO;

import java.util.regex.Pattern;


public class ValidadorUsuario {
    //se crean las expresiones regulares para hacer las validaciones
    private static final Pattern CORREO_PATTERN = Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
    private static final Pattern SOLO_LETRAS_PATTERN = Pattern.compile("^[a-zA-Z\\s]+$");
    private static final Pattern CONTRASENIA_PATTERN = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\W)(?=.*\\d).{8,}$");

    public static String validarUsuario(Usuario usuario, UsuariosDAO dao) {
        //se crea el metodo que va a validar que todos los campos sean correctos

        if (usuario.getNombre() == null || usuario.getNombre().trim().isEmpty())
            //si el nombre de usuario es nulo o viene vacio se retorna un mensaje indicando esto
            return "El nombre no puede estar vacío.";

        if (!SOLO_LETRAS_PATTERN.matcher(usuario.getNombre()).matches())
            //se valida que el nombre del usuario solo contenga letras, esto se hace con ayuda de la expresion regular
            // en caso de que no contenga solo letras se retorna un mensaje indicando esto
            return "El nombre solo debe contener letras.";

        if (usuario.getDocumento() <= 0)
            //se valida que el documenot del usuario sea un numeros positivo
            return "El documento debe ser un número positivo.";

        String telefonoStr = String.valueOf(usuario.getTelefono());//se trasnforma el telefono a un dato tipo String
        if (telefonoStr.length() != 10)
            //se valida que el telefono del usuario tenga exacmanete 10 acarcteres
            return "El teléfono debe tener exactamente 10 dígitos.";

        if (usuario.getCorreo() == null || !CORREO_PATTERN.matcher(usuario.getCorreo()).matches())
            //se valida que el formato del correo sea valido
            return "El correo no tiene un formato válido.";

        if (usuario.getContrasenia() == null || !CONTRASENIA_PATTERN.matcher(usuario.getContrasenia()).matches())
            //se valida que el formato de la contraseña del usuario sea valido
            return "La contraseña debe tener mínimo 8 caracteres, al menos una mayúscula, una minúscula, un número y un carácter especial.";

        //se valida que el correo no se haya registrado previamente
        Usuario porCorreo = dao.getByCorreo(usuario.getCorreo());
        if (porCorreo != null && porCorreo.getId() != usuario.getId())
            return "El correo ya está registrado.";
        
        //se valida que el documento no se haya registrado previamente
        Usuario porDoc = dao.getByDocumento(usuario.getDocumento());
        if (porDoc != null && porDoc.getId() != usuario.getId())
            return "El documento ya está registrado.";

        return null; //si se cumple con todos los requisitos se retorna null
    }
}
