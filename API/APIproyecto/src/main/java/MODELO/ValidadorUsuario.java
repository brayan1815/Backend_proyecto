package MODELO;

import java.util.List;
import java.util.regex.Pattern;

public class ValidadorUsuario {
    // Expresiones regulares para validar formatos específicos
    private static final Pattern CORREO_PATTERN = Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$"); // valida formato de correo electrónico
    private static final Pattern SOLO_LETRAS_PATTERN = Pattern.compile("^[a-zA-Z\\s]+$"); // valida solo letras y espacios en el nombre
    private static final Pattern CONTRASENIA_PATTERN = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\W)(?=.*\\d).{8,}$"); 
    // valida contraseña con al menos 8 caracteres, una mayúscula, una minúscula, un número y un carácter especial

    // Método que valida los datos del objeto Usuario
    // Parámetros: usuario (objeto a validar), dao (para validar existencia de datos en BD), actualizar (boolean para validar contraseña solo si no es actualización)
    public static String validarUsuario(Usuario usuario, UsuariosDAO dao, boolean actualizar) {

        // Validar que el nombre no sea nulo ni vacío
        if (usuario.getNombre() == null || usuario.getNombre().trim().isEmpty())
            return "El nombre no puede estar vacío.";

        // Validar que el nombre contenga solo letras y espacios
        if (!SOLO_LETRAS_PATTERN.matcher(usuario.getNombre()).matches())
            return "El nombre solo debe contener letras.";

        // Validar que el documento sea un número positivo
        if (usuario.getDocumento() <= 0)
            return "El documento debe ser un número positivo.";

        // Convertir el teléfono a String para validar longitud
        String telefonoStr = String.valueOf(usuario.getTelefono());
        // Validar que el teléfono tenga exactamente 10 dígitos
        if (telefonoStr.length() != 10)
            return "El teléfono debe tener exactamente 10 dígitos.";

        // Validar que el correo no sea nulo y que tenga formato válido
        if (usuario.getCorreo() == null || !CORREO_PATTERN.matcher(usuario.getCorreo()).matches())
            return "El correo no tiene un formato válido.";

        // Si NO es actualización, validar contraseña con el patrón
        if (!actualizar) {
            if (usuario.getContrasenia() == null || !CONTRASENIA_PATTERN.matcher(usuario.getContrasenia()).matches())
                return "La contraseña debe tener mínimo 8 caracteres, al menos una mayúscula, una minúscula, un número y un carácter especial.";
        }

        // Validar que el correo no esté registrado por otro usuario diferente
        Usuario porCorreo = dao.getByCorreo(usuario.getCorreo());
        if (porCorreo != null && porCorreo.getId() != usuario.getId())
            return "El correo ya está registrado.";

        // Validar que el documento no esté registrado por otro usuario diferente
        Usuario porDoc = dao.getByDocumento(usuario.getDocumento());
        if (porDoc != null && porDoc.getId() != usuario.getId())
            return "El documento ya está registrado.";

        // Si pasa todas las validaciones, retornar null (sin errores)
        return null;
    }
    
    public static String validarEliminacionUsuario(int idUsuario){
        ReservasDAO resDao=new ReservasDAO();
        
        List<Reserva> reservas=resDao.getByIdUsuario(idUsuario);
        
        if(reservas.size()>0){
            return "No se puede eliminar el usuario porque tiene reservas asociadas";
        }
        return null;
        
    }
}
