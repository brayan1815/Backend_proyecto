/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODELO;

import BD.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ImagenesDAO {

    public int post(String rutaRelativa) {
        //metodo para insertar una imagen
        int idGenerado = -1;

        try (Connection conn = ConexionBD.getConnection()) {
            //se ejecuta el insert y se devuelve el id generado automaticamente
            String sql = "INSERT INTO imagenes (ruta) VALUES (?)";
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, rutaRelativa);
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                //lee el id generado
                idGenerado = rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();//si ocurre un error se imprime
        }

        return idGenerado;//se retorna el id generaod
    }

    public Imagen getById(int id) {
        //metodo para obtener la imagen por id
        Imagen imagen = null;

        try (Connection conn = ConexionBD.getConnection()) {
            //se crea la consulta SQL
            String sql = "SELECT ruta FROM imagenes WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);//se prepara la consulta
            stmt.setInt(1, id);//se reemplazan lso paramstros de la consulta

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                //si se encuentra la iamgen se le asigna a la variable imagen el objeto de esat
                imagen = new Imagen(id, rs.getString("ruta")); // solo la ruta relativa
            }

        } catch (SQLException e) {
            e.printStackTrace();//si ocurre un error se imprime
        }

        return imagen;//se retorna la imagen
    }
}
