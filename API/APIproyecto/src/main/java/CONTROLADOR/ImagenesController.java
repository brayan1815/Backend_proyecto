
package CONTROLADOR;

import MODELO.ImagenesDAO;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

@Path("/imagenes")
public class ImagenesController {
    
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response crearImagen(
        @FormDataParam("archivo") InputStream inputStream,
        @FormDataParam("archivo") FormDataContentDisposition fileDetail
    ){
        try {
            // Carpeta relativa al proyecto
            String carpeta = System.getProperty("user.dir") + "/imagenes/";

            // Crear carpeta si no existe
            File dir = new File(carpeta);
            if (!dir.exists()) dir.mkdirs();

            // Generar nombre único
            String nombreArchivo = UUID.randomUUID().toString() + "_" + fileDetail.getFileName();
            String rutaCompleta = carpeta + nombreArchivo;
            String rutaRelativa = "imagenes/" + nombreArchivo;

            // Guardar archivo físicamente
            Files.copy(inputStream, Paths.get(rutaCompleta), StandardCopyOption.REPLACE_EXISTING);

            // Registrar ruta en la base de datos
            ImagenesDAO dao = new ImagenesDAO();
            int idImagen = dao.post(rutaRelativa);

            // Responder con ID y ruta
            return Response.status(Response.Status.CREATED)
                .entity("{\"id_imagen\":" + idImagen + ", \"ruta\":\"" + rutaRelativa + "\"}")
                .build();

        } catch (IOException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error al guardar imagen").build();
        }
    }
}
