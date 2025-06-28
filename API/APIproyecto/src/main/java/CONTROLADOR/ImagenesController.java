
package CONTROLADOR;

import MODELO.Imagen;
import MODELO.ImagenesDAO;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

@Path("/imagenes")
public class ImagenesController {

    
    private final String carpeta;

    public ImagenesController() {
        carpeta = "C:/imagenes-api/";
        File dir = new File(carpeta);
        if (!dir.exists()) {
            dir.mkdirs(); // 🔧 Crea carpeta si no existe
        }
    }
    

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response crearImagen(
        @FormDataParam("archivo") InputStream inputStream,
        @FormDataParam("archivo") FormDataContentDisposition fileDetail
    ) {
        try {
            // Crear carpeta si no existe
            File dir = new File(carpeta);
            if (!dir.exists()) dir.mkdirs();

            // Generar nombre único
            String nombreArchivo = UUID.randomUUID().toString() + "_" + fileDetail.getFileName();
            String rutaCompleta = carpeta + nombreArchivo;
            String rutaRelativa = "imagenes/" + nombreArchivo;
            
            System.out.println("RUTA COMPLETAAAAAAAAAAAA: "+rutaCompleta);

            // Guardar físicamente
            Files.copy(inputStream, Paths.get(rutaCompleta), StandardCopyOption.REPLACE_EXISTING);

            // Guardar solo la ruta relativa en la BD
            ImagenesDAO dao = new ImagenesDAO();
            int id = dao.post(rutaRelativa);

            return Response.status(Response.Status.CREATED)
                .entity("{\"id_imagen\":" + id + ", \"ruta\":\"" + rutaRelativa + "\"}")
                .build();
        } catch (IOException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error al guardar imagen").build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerImagenPorId(@PathParam("id") int id) {
        ImagenesDAO dao = new ImagenesDAO();
        Imagen imagen = dao.getById(id);

        if (imagen != null) {
            String ruta = imagen.getRuta(); // ya es relativa: "imagenes/nombre.png"
            String json = String.format("{\"id\":%d,\"ruta\":\"%s\"}", imagen.getId(), ruta);
            return Response.ok(json).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                .entity("Imagen no encontrada").build();
        }
    }
}
