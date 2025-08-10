package MODELO;

import BD.ConexionBD;
import java.sql.Connection;//se importa la clase que representa la conexión a la base de datos
import java.sql.PreparedStatement;//se importa la clase que permite preparar consultas SQL con parámetros
import java.sql.ResultSet;//se importa la clase que permite manejar los resultados de las consultas
import java.sql.SQLException;//se importa la clase para manejar errores relacionados con SQL
import java.util.ArrayList;//se importa la clase para manejar listas dinámicas
import java.util.List;//se importa la interfaz List para manejar colecciones de datos

//se define la clase MetodosPagoDAO, que contiene los métodos para interactuar con la tabla metodos_pago en la base de datos
public class MetodosPagoDAO {
    
    public List<MetodoPago> getAll() {
        //se crea el método getAll, que retorna una lista con todos los métodos de pago de la base de datos
        
        List<MetodoPago> lista = new ArrayList<>(); //se crea la lista que almacenará los métodos de pago

        try (Connection conn = ConexionBD.getConnection()) { //se abre la conexión a la base de datos
            String sql = "SELECT * FROM metodos_pago"; //se crea la consulta SQL para obtener todos los métodos de pago
            PreparedStatement stmt = conn.prepareStatement(sql); //se prepara la consulta SQL
            ResultSet rs = stmt.executeQuery(); //se ejecuta la consulta y se almacena el resultado en rs

            while (rs.next()) { //se recorre cada registro obtenido
                //se crea un objeto MetodoPago y se asignan sus valores con los datos obtenidos
                MetodoPago metodo = new MetodoPago();
                metodo.setId(rs.getInt("id")); //se asigna el id obtenido a la propiedad id del objeto
                metodo.setMetodoPago(rs.getString("metodo_pago")); //se asigna el nombre del método de pago
                
                lista.add(metodo); //se agrega el objeto metodo a la lista
            }

        } catch (SQLException e) {
            e.printStackTrace(); //si ocurre un error, se imprime para facilitar la depuración
        }

        return lista; //se retorna la lista con todos los métodos de pago
    }
}
