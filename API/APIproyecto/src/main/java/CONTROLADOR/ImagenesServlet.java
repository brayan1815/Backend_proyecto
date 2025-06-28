/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CONTROLADOR;

import javax.servlet.http.*;
import java.io.*;
import javax.servlet.annotation.WebServlet;

public class ImagenesServlet extends HttpServlet {

   private final String RUTA_IMAGENES;

public ImagenesServlet() {
    String ruta = "C:/imagenes-api";
    File carpeta = new File(ruta);
    if (!carpeta.exists()) {
        carpeta.mkdirs(); // 🔧 Crea la carpeta si no existe
    }
    RUTA_IMAGENES = ruta;
}




    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String pathInfo = request.getPathInfo(); // /archivo.png
        if (pathInfo == null || pathInfo.equals("/")) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Nombre de archivo requerido");
            return;
        }

        File imagen = new File(RUTA_IMAGENES, pathInfo);
        if (!imagen.exists()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Archivo no encontrado");
            return;
        }

        String mime = getServletContext().getMimeType(imagen.getName());
        if (mime == null) {
            mime = "application/octet-stream";
        }

        response.setContentType(mime);
        response.setContentLengthLong(imagen.length());

        try (FileInputStream in = new FileInputStream(imagen);
             OutputStream out = response.getOutputStream()) {
            byte[] buffer = new byte[4096];
            int bytes;
            while ((bytes = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytes);
            }
        }
    }
}
