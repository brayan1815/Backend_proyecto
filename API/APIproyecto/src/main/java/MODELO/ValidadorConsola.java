
package MODELO;

import java.util.List;

public class ValidadorConsola {
    
    
    
    public static String validarConsola(Consola consola) {
        ConsolasDAO dao=new ConsolasDAO();

        //VALIDAR QUE EL NOMBRE DE LA CONSOLA NO VENGA VACIO
        if (consola.getNombre() == null || consola.getNombre().trim().isEmpty()) {
            return "{\"error\": \"El nombre no puede estar vacío.\"}";
        }
        //validar que el nombre de la consola solo contenga nombres
        if (!consola.getNombre().matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$")) {
            return "{\"error\": \"El nombre solo puede contener letras.\"}";
        }
        //validar que el nombre de la consola no tenga mas de 30 caracteres
        if (consola.getNombre().length() > 30) {
            return "{\"error\": \"El nombre no puede tener más de 30 caracteres.\"}";
        }

        // validar que la descripcion de la consola no este vacia
        if (consola.getDescripcion() == null || consola.getDescripcion().trim().isEmpty()) {
            return "{\"error\": \"La descripción no puede estar vacía.\"}";
        }
        //validar que la descripcion de la consola tenga minimo 15 carcteres
        if (consola.getDescripcion().length() < 15) {
            return "{\"error\": \"La descripción debe tener mínimo 15 caracteres.\"}";
        }

        //validar que el id de tipo no sea cero ni vacio
        if (consola.getId_tipo() <= 0) {
            return "{\"error\": \"El id_tipo no puede estar vacío o en cero.\"}";
        }

        // Vvalidar que el id de estado no sea cero ni este vacio
        if (consola.getId_estado() <= 0) {
            return "{\"error\": \"El id_estado no puede estar vacío o en cero.\"}";
        }

        // validar que el id de imagen no sea cero ni este vacio
        if (consola.getId_imagen() <= 0) {
            return "{\"error\": \"El id_imagen no puede estar vacío o en cero.\"}";
        }
        
        Consola cons=dao.getByNumeroSerie(consola.getNumero_serie());
        
        if(cons!=null && cons.getId()!=consola.getId()){
            return "{\"error\": \"Ya se encuentra una consola registrada con este numero de serie.\"}";
        }

        return null; //sei se pasan todos los filtros se retorna null
    }
    
//    public static String validarEliminacion(int idConsola) {
//        ReservasDAO dao = new ReservasDAO();
//        List<Reserva> reservas = dao.getByIdConsola(idConsola);
//
//        if (!reservas.isEmpty()) {
//            return "{\"error\": \"No se puede eliminar la consola porque tiene reservas asociadas.\"}";
//        }
//
//        return null; // Se puede eliminar
//    }
}
