package MODELO;

import BD.ConexionBD;
import java.sql.Connection; // para manejar la conexión a la base de datos
import java.sql.PreparedStatement; // para preparar consultas SQL con parámetros
import java.sql.ResultSet; // para manejar los resultados de las consultas
import java.sql.SQLException; // para manejar errores de SQL
import java.util.ArrayList; // para crear listas dinámicas
import java.util.List; // interfaz para listas

// clase ProductosDAO para manejar operaciones CRUD sobre la tabla productos
public class ProductosDAO {

    public boolean post(Producto producto) {
        // método para insertar un nuevo producto en la base de datos
        boolean exito = false; // indica si la operación fue exitosa

        try (Connection conn = ConexionBD.getConnection()) { // abre la conexión a la base de datos
            // consulta SQL para insertar un nuevo registro en productos
            String sql = "INSERT INTO productos (nombre, descripcion, precio, cantidades_disponibles, id_estado_producto, id_imagen) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql); // prepara la consulta

            // asigna los valores recibidos a los parámetros de la consulta
            stmt.setString(1, producto.getNombre());
            stmt.setString(2, producto.getDescripcion());
            stmt.setDouble(3, producto.getPrecio());
            stmt.setInt(4, producto.getCantidades_disponibles());
            stmt.setInt(5, producto.getId_estado_producto());
            stmt.setInt(6, producto.getId_imagen());

            // ejecuta la consulta y verifica si se insertó alguna fila
            int filas = stmt.executeUpdate();
            exito = filas > 0;

        } catch (SQLException e) {
            e.printStackTrace(); // imprime el error si ocurre alguna excepción
        }

        return exito; // retorna true si se insertó, false si no
    }

    public List<Producto> getAll() {
        // método para obtener todos los productos de la base de datos
        List<Producto> lista = new ArrayList<>(); // lista que almacenará los productos

        try (Connection conn = ConexionBD.getConnection()) { // abre la conexión
            String sql = "SELECT * FROM productos"; // consulta SQL para obtener todos los productos
            PreparedStatement stmt = conn.prepareStatement(sql); // prepara la consulta
            ResultSet rs = stmt.executeQuery(); // ejecuta la consulta

            while (rs.next()) { // recorre cada registro del resultado
                Producto p = new Producto(); // crea un objeto Producto
                // asigna los valores de la fila a los atributos del objeto
                p.setId(rs.getInt("id"));
                p.setNombre(rs.getString("nombre"));
                p.setDescripcion(rs.getString("descripcion"));
                p.setPrecio(rs.getDouble("precio"));
                p.setCantidades_disponibles(rs.getInt("cantidades_disponibles"));
                p.setId_estado_producto(rs.getInt("id_estado_producto"));
                p.setId_imagen(rs.getInt("id_imagen"));

                lista.add(p); // agrega el producto a la lista
            }

        } catch (Exception e) {
            e.printStackTrace(); // imprime error si hay alguna excepción
        }

        return lista; // retorna la lista con todos los productos
    }

    public Producto getById(int id) {
        // método para obtener un producto por su id
        Producto producto = null; // inicialmente es null si no se encuentra

        try (Connection conn = ConexionBD.getConnection()) { // abre la conexión
            String sql = "SELECT * FROM productos WHERE id = ?"; // consulta SQL con parámetro
            PreparedStatement stmt = conn.prepareStatement(sql); // prepara la consulta
            stmt.setInt(1, id); // asigna el id recibido al parámetro

            ResultSet rs = stmt.executeQuery(); // ejecuta la consulta
            if (rs.next()) { // si se encontró un producto
                producto = new Producto(); // crea el objeto Producto
                // asigna los valores de la fila a los atributos del objeto
                producto.setId(rs.getInt("id"));
                producto.setNombre(rs.getString("nombre"));
                producto.setDescripcion(rs.getString("descripcion"));
                producto.setPrecio(rs.getDouble("precio"));
                producto.setCantidades_disponibles(rs.getInt("cantidades_disponibles"));
                producto.setId_estado_producto(rs.getInt("id_estado_producto"));
                producto.setId_imagen(rs.getInt("id_imagen"));
            }
        } catch (SQLException e) {
            e.printStackTrace(); // imprime el error si ocurre excepción
        }

        return producto; // retorna el producto encontrado o null si no existe
    }

    public boolean put(Producto producto) {
        // método para actualizar un producto existente
        boolean actualizado = false; // indica si la actualización fue exitosa

        try (Connection conn = ConexionBD.getConnection()) { // abre la conexión
            // consulta SQL para actualizar los datos de un producto según su id
            String sql = "UPDATE productos SET nombre = ?, descripcion = ?, precio = ?, cantidades_disponibles = ?, id_estado_producto = ?, id_imagen = ? WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql); // prepara la consulta

            // asigna los nuevos valores a los parámetros de la consulta
            stmt.setString(1, producto.getNombre());
            stmt.setString(2, producto.getDescripcion());
            stmt.setDouble(3, producto.getPrecio());
            stmt.setInt(4, producto.getCantidades_disponibles());
            stmt.setInt(5, producto.getId_estado_producto());
            stmt.setInt(6, producto.getId_imagen());
            stmt.setInt(7, producto.getId());

            // ejecuta la actualización y verifica si se afectó alguna fila
            actualizado = stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace(); // imprime el error si ocurre excepción
        }

        return actualizado; // retorna true si se actualizó, false si no
    }

    public boolean Delete(int id) {
        // método para eliminar un producto según su id
        boolean eliminado = false; // indica si la eliminación fue exitosa

        try (Connection conn = ConexionBD.getConnection()) { // abre la conexión
            String sql = "DELETE FROM productos WHERE id = ?"; // consulta SQL para eliminar el producto
            PreparedStatement stmt = conn.prepareStatement(sql); // prepara la consulta
            stmt.setInt(1, id); // asigna el id recibido al parámetro

            int filas = stmt.executeUpdate(); // ejecuta la eliminación
            eliminado = filas > 0; // true si se eliminó al menos una fila

        } catch (SQLException e) {
            e.printStackTrace(); // imprime error si ocurre excepción
        }

        return eliminado; // retorna true si se eliminó, false si no
    }
}
