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

@Path("/imagenes")//se define la ruta base para los endpoints de esta clase
public class ImagenesController {

    //se declara la variable para almacenar la ruta física donde se guardarán las imágenes
    private final String carpeta;

    public ImagenesController() {
        //se define la ruta física para almacenar las imágenes
        carpeta = "C:/imagenes-api/";
        
        //se verifica si la carpeta existe, en caso contrario se crea
        File dir = new File(carpeta);
        if (!dir.exists()) {
            dir.mkdirs();//crea la carpeta si no existe
        }
    }
    
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response crearImagen(
        @FormDataParam("archivo") InputStream inputStream,//se recibe el flujo de datos del archivo
        @FormDataParam("archivo") FormDataContentDisposition fileDetail //se recibe información adicional del archivo como nombre y tipo
    ) {
        try {
            //se asegura que la carpeta de destino exista
            File dir = new File(carpeta);
            if (!dir.exists()) dir.mkdirs();

            //se genera un nombre único para el archivo usando UUID para evitar duplicados
            String nombreArchivo = UUID.randomUUID().toString() + "_" + fileDetail.getFileName();
            
            //se define la ruta completa en disco y la ruta relativa para base de datos
            String rutaCompleta = carpeta + nombreArchivo;
            String rutaRelativa = "imagenes/" + nombreArchivo;
            
           
            //se guarda físicamente el archivo en el servidor
            Files.copy(inputStream, Paths.get(rutaCompleta), StandardCopyOption.REPLACE_EXISTING);

            //se guarda la ruta relativa en la base de datos y se obtiene el ID de la imagen
            ImagenesDAO dao = new ImagenesDAO();
            int id = dao.post(rutaRelativa);
            
            //se retorna respuesta con código 201 CREATED y datos de la imagen guardada
            return Response.status(Response.Status.CREATED)
                .entity("{\"id_imagen\":" + id + ", \"ruta\":\"" + rutaRelativa + "\"}")
                .build();
        } catch (IOException e) {
            //en caso de error se imprime el stacktrace y se retorna un código 500 con mensaje de error
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error al guardar imagen").build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerImagenPorId(@PathParam("id") int id) {
        //se crea el método para obtener una imagen por su ID
        
        ImagenesDAO dao = new ImagenesDAO();//instancia del DAO
        Imagen imagen = dao.getById(id);//se obtiene la imagen con el ID proporcionado
        
        //si la imagen existe se retorna su información en formato JSON
        if (imagen != null) {
            String ruta = imagen.getRuta();//se obtiene la ruta relativa
            String json = String.format("{\"id\":%d,\"ruta\":\"%s\"}", imagen.getId(), ruta);
            return Response.ok(json).build();
        } else {
            //si no se encuentra la imagen se retorna un código 404 con mensaje de error
            return Response.status(Response.Status.NOT_FOUND)
                .entity("Imagen no encontrada").build();
        }
    }
}
