/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODELO;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ImagenesDAO {
    // Método que guarda la ruta de una imagen en la base de datos y devuelve el ID generado
    public int post(String rutaRelativa) {
        // Variable para almacenar el ID autogenerado de la imagen (lo que devuelve la BD)
        int idGenerado = -1;

        try (Connection conn = ConexionBD.getConnection()) {
            // Consulta SQL para insertar la ruta de la imagen en la tabla 'imagenes'
            String sql = "INSERT INTO imagenes (ruta) VALUES (?)";
            // Preparamos la consulta indicando que queremos obtener las llaves generadas (ID autoincremental)
            PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            stmt.setString(1, rutaRelativa);
            stmt.executeUpdate();
            // Obtenemos el ID generado automáticamente por la base de datos
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                idGenerado = rs.getInt(1); // Tomamos el primer valor generado (el ID)
            }
        } catch (SQLException e) {
            // Si ocurre un error con la base de datos, lo mostramos en consola
            e.printStackTrace();
        }

        // Retornamos el ID generado (o -1 si hubo un error)
        return idGenerado;
    }
    
    public Imagen getById(int id) {
    Imagen imagen = null;
    
    try (Connection conn = ConexionBD.getConnection()) {
        String sql = "SELECT ruta FROM imagenes WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        
        if (rs.next()) {
            String rutaRelativa = rs.getString("ruta");
            // Aquí generamos la ruta absoluta, como la usaste al guardar
            String rutaCompleta = System.getProperty("user.dir") + File.separator + rutaRelativa.replace("/", File.separator);
            imagen = new Imagen(id, rutaCompleta);
        }
        
    } catch (SQLException e) {
        e.printStackTrace();
    }
    
    return imagen;
}
}
