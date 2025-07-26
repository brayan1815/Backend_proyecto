
package MODELO;

import java.util.ArrayList;
import java.util.List;


public class ConsolasServices {
    //se crean las intancias de las clses consolasDAO y TiposDAO
    ConsolasDAO consolaDAO = new ConsolasDAO();
    TiposDAO tipoDAO = new TiposDAO();
    
    public List<ConsolaDTO> obtenerConsolasConPrecio() {
        //se crea el metodo obtenerConsolasConPrecio, este metodo devolvera las consolas son su repsectivo precio
        List<Consola> consolas = consolaDAO.getAll();//se obttienene todas las consolas de la base de datos
        List<ConsolaDTO> listaDTO = new ArrayList<>();//se crea la lisraDTO, esta devolvera la lista de consolas cons u precio

        for (Consola c : consolas) {
            //se recorre cada una de las consolas
            Tipo t = tipoDAO.getById(c.getId_tipo());//se obtiene el tipo correspondiente a la consola

            ConsolaDTO dto = new ConsolaDTO(//se crea un objeto consolasDTO
                c.getId(),
                c.getNombre(),
                c.getDescripcion(),
                t.getPrecio_hora(),
                c.getId_estado(),
                c.getId_imagen()
            );

            listaDTO.add(dto);//se agrega a la lista el objetoDTO
        }

        return listaDTO;//se retorna la lista DTO
    }
}
