
package MODELO;

import java.util.ArrayList;
import java.util.List;


public class ConsolasServices {
    ConsolasDAO consolaDAO = new ConsolasDAO();
    TiposDAO tipoDAO = new TiposDAO();
    
    public List<ConsolaDTO> obtenerConsolasConPrecio() {
        List<Consola> consolas = consolaDAO.getAll();
        List<ConsolaDTO> listaDTO = new ArrayList<>();

        for (Consola c : consolas) {
            Tipo t = tipoDAO.getById(c.getId_tipo());

            ConsolaDTO dto = new ConsolaDTO(
                c.getId(),
                c.getNombre(),
                c.getDescripcion(),
                t.getPrecio_hora(),
                c.getId_estado(),
                c.getId_imagen()
            );

            listaDTO.add(dto);
        }

        return listaDTO;
    }
}
