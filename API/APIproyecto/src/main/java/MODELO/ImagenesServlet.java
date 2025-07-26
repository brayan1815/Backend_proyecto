/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODELO;

import javax.servlet.http.*;
import java.io.*;

public class ImagenesServlet extends HttpServlet {

   private final String RUTA_IMAGENES;

public ImagenesServlet() {
    //se inicializa la ruta de la imagen y se crea la capeta en caso de que no exista
    String ruta = "C:/imagenes-api";
    File carpeta = new File(ruta);
    if (!carpeta.exists()) {
        carpeta.mkdirs();
    }
    RUTA_IMAGENES = ruta;
}




    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String pathInfo = request.getPathInfo(); //Obtiene la parte de la URL que está después del nombre del servlet.
        if (pathInfo == null || pathInfo.equals("/")) {
            //si no se especifico el nombre del archivo se retorna error 400
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Nombre de archivo requerido");
            return;
        }

        //Crea un objeto File que representa la ruta completa del archivo en el sistema de archivos.
        File imagen = new File(RUTA_IMAGENES, pathInfo);
        if (!imagen.exists()) {
            //si el archivo no existe se retorna un error 400
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Archivo no encontrado");
            return;
        }

        String mime = getServletContext().getMimeType(imagen.getName());//se determina el tipo del archivo
        if (mime == null) {
            mime = "application/octet-stream";//si no se logra obtener el tipo del archivo se asigan uno generico que son
                                              //datos binarios
        }
        // Establece el tipo de contenido que se va a enviar en la respuesta HTTP, basado en el MIME
        response.setContentType(mime);
        //informa al navegador cuantos bytes tiene la imagen
        response.setContentLengthLong(imagen.length());

        try (FileInputStream in = new FileInputStream(imagen);// Abre un flujo de entrada (in) para leer la imagen desde el disco.
             OutputStream out = response.getOutputStream()) {//Abre un flujo de salida (out) para escribir la imagen en la respuesta.
            byte[] buffer = new byte[4096];
            int bytes;
            while ((bytes = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytes);
            }
        }
    }
}
