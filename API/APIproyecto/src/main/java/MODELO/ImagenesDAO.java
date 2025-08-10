package MODELO;

import BD.ConexionBD;
import java.sql.Connection;//se importa la clase que representa la conexión a la base de datos
import java.sql.PreparedStatement;//se importa la clase que permite preparar consultas SQL con parámetros
import java.sql.ResultSet;//se importa la clase que permite manejar los resultados de las consultas
import java.sql.SQLException;//se importa la clase para manejar errores relacionados con SQL

public class ImagenesDAO {

    public int post(String rutaRelativa) {
        //se crea el método post para insertar una nueva imagen en la base de datos
        int idGenerado = -1;//se inicializa en -1 para indicar que no se generó ningún ID aún

        try (Connection conn = ConexionBD.getConnection()) {//se abre la conexión a la base de datos
            //se crea la consulta SQL para insertar la ruta relativa de la imagen
            String sql = "INSERT INTO imagenes (ruta) VALUES (?)";
            PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);//se prepara la consulta para devolver la llave generada
            stmt.setString(1, rutaRelativa);//se asigna el parámetro con la ruta relativa de la imagen
            stmt.executeUpdate();//se ejecuta la consulta de inserción

            ResultSet rs = stmt.getGeneratedKeys();//se obtienen las llaves generadas por la base de datos
            if (rs.next()) {//si hay un id generado
                idGenerado = rs.getInt(1);//se lee el id generado y se asigna a la variable
            }

        } catch (SQLException e) {
            e.printStackTrace();//si ocurre un error se imprime la excepción
        }

        return idGenerado;//se retorna el id generado o -1 si hubo error
    }

    public Imagen getById(int id) {
        //se crea el método getById para obtener una imagen específica por su id
        Imagen imagen = null;//se inicializa en null por si no se encuentra la imagen

        try (Connection conn = ConexionBD.getConnection()) {//se abre la conexión a la base de datos
            //se crea la consulta SQL para obtener la ruta de la imagen según el id
            String sql = "SELECT ruta FROM imagenes WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);//se prepara la consulta con parámetros
            stmt.setInt(1, id);//se asigna el valor del id al parámetro de la consulta

            ResultSet rs = stmt.executeQuery();//se ejecuta la consulta y se obtiene el resultado

            if (rs.next()) {//si se encontró la imagen con ese id
                imagen = new Imagen(id, rs.getString("ruta"));//se crea el objeto Imagen con id y ruta
            }

        } catch (SQLException e) {
            e.printStackTrace();//si ocurre un error se imprime la excepción
        }

        return imagen;//se retorna la imagen encontrada o null si no existe
    }
}
