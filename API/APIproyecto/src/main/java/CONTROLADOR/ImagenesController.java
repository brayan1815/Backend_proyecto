
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

    // Se declara una variable para guardar la ruta donde se almacenarán las imágenes físicamente.
    private final String carpeta;

    public ImagenesController() {
        //se define la ruta de almacenamiento de imágenes y crea la carpeta si no existe.
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
        //se captura el contenido del archivo que se envia e informacion del archivo como nombre o detalles 
    ) {
        try {
            // Crear carpeta si no existe
            File dir = new File(carpeta);
            if (!dir.exists()) dir.mkdirs();//se asegura nuevamente de que el archivo exista 

            //Crea un nombre único para el archivo con UUID para evitar duplicados.
            String nombreArchivo = UUID.randomUUID().toString() + "_" + fileDetail.getFileName();
            // Guarda la ruta completa (para guardarlo físicamente) y la relativa (para guardar en la BD).
            String rutaCompleta = carpeta + nombreArchivo;
            String rutaRelativa = "imagenes/" + nombreArchivo;
            
            System.out.println("RUTA COMPLETAAAAAAAAAAAA: "+rutaCompleta);//se imprime la ruta completa donde se gaurdara la imagen

            // se copia el archivo desde el inputStream a la ruta física del servidor.
            Files.copy(inputStream, Paths.get(rutaCompleta), StandardCopyOption.REPLACE_EXISTING);

            // Llama al DAO para guardar la ruta relativa en la base de datos y obtener el ID generado.
            ImagenesDAO dao = new ImagenesDAO();
            int id = dao.post(rutaRelativa);
            
            // Devuelve una respuesta 201 CREATED con un JSON que contiene el ID y la ruta de la imagen.
            return Response.status(Response.Status.CREATED)
                .entity("{\"id_imagen\":" + id + ", \"ruta\":\"" + rutaRelativa + "\"}")
                .build();
        } catch (IOException e) {
            //en caso de error de imprime y se devuelve un codigo 500
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error al guardar imagen").build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerImagenPorId(@PathParam("id") int id) {
        //metodo para obtener la imagen por ID
        ImagenesDAO dao = new ImagenesDAO();
        //Busca la imagen con el ID proporcionado.
        Imagen imagen = dao.getById(id);
        
        // Si encuentra la imagen, devuelve su ID y ruta. Si no, envía un 404 NOT FOUND.
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
