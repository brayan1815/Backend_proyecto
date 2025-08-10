package MODELO;

import BD.ConexionBD;
import static BD.ConexionBD.getConnection;
import java.sql.Connection; // se importa la clase que representa la conexión a la base de datos
import java.sql.PreparedStatement; // se importa la clase que permite preparar consultas SQL con parámetros
import java.sql.ResultSet; // se importa la clase que permite manejar los resultados de las consultas
import java.sql.SQLException; // se importa la clase para manejar errores relacionados con SQL
import java.util.ArrayList; // se importa la clase para manejar listas dinámicas
import java.util.List; // se importa la interfaz List para manejar colecciones de datos

// se define la clase PagosDAO, que contiene los métodos para interactuar con la tabla pagos en la base de datos
public class PagosDAO {

    public boolean post(int idFactura, int idMetodo) {
        // se crea el método post para registrar un nuevo pago en la base de datos
        
        boolean exito = false; // variable que indicará si la operación fue exitosa
        
        try (Connection conn = ConexionBD.getConnection()) { // se abre la conexión a la base de datos
            String sql = "INSERT INTO pagos (id_factura, id_metodo) VALUES (?, ?)"; // consulta SQL para insertar un pago
            PreparedStatement stmt = conn.prepareStatement(sql); // se prepara la consulta SQL
            
            stmt.setInt(1, idFactura); // se asigna el id de la factura al primer parámetro
            stmt.setInt(2, idMetodo); // se asigna el id del método de pago al segundo parámetro
            
            exito = stmt.executeUpdate() > 0; // se ejecuta la consulta y se verifica si se insertó alguna fila
            
        } catch (SQLException e) {
            e.printStackTrace(); // si ocurre un error, se imprime la traza para facilitar la depuración
        }
        
        return exito; // se retorna true si la inserción fue exitosa, false en caso contrario
    }
    
    public List<Pago> obtenerPagosPorMetodo(int idMetodo) {
        // se crea el método obtenerPagosPorMetodo, que retorna una lista con los pagos que usaron un método de pago específico
        
        List<Pago> lista = new ArrayList<>(); // se crea la lista que almacenará los pagos
        
        try (Connection con = getConnection()) { // se abre la conexión a la base de datos
            String sql = "SELECT * FROM pagos WHERE id_metodo = ?"; // consulta SQL para obtener pagos por método de pago
            PreparedStatement ps = con.prepareStatement(sql); // se prepara la consulta SQL
            
            ps.setInt(1, idMetodo); // se asigna el id del método de pago al parámetro
            
            ResultSet rs = ps.executeQuery(); // se ejecuta la consulta y se obtiene el resultado
            
            while (rs.next()) { // se recorren todos los resultados obtenidos
                Pago p = new Pago(); // se crea un objeto Pago por cada fila del resultado
                
                p.setId(rs.getInt("id")); // se asigna el id del pago
                p.setId_factura(rs.getInt("id_factura")); // se asigna el id de la factura asociada al pago
                p.setId_metodo(rs.getInt("id_metodo")); // se asigna el id del método de pago
                
                lista.add(p); // se agrega el pago a la lista
            }
            
        } catch (SQLException e) {
            e.printStackTrace(); // si ocurre un error, se imprime para facilitar la depuración
        }
        
        return lista; // se retorna la lista con todos los pagos encontrados
    }
}
