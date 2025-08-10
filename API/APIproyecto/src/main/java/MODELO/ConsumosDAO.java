package MODELO; //se define el paquete MODELO donde estará contenida esta clase

//se importan las clases necesarias para la conexión y manejo de base de datos
import BD.ConexionBD; //importa la clase para establecer la conexión con la base de datos
import java.sql.Connection; //importa la clase Connection para manejar la conexión con la base de datos
import java.sql.PreparedStatement; //importa la clase PreparedStatement para ejecutar sentencias SQL parametrizadas
import java.sql.ResultSet; //importa la clase ResultSet para manejar los resultados de las consultas SQL
import java.sql.SQLException; //importa la clase SQLException para manejar errores de SQL
import java.util.ArrayList; //importa la clase ArrayList para crear listas dinámicas
import java.util.List; //importa la interfaz List para manejar listas

//se define la clase ConsumosDAO que maneja las operaciones CRUD sobre la tabla consumos en la base de datos
public class ConsumosDAO {

    //método para registrar un nuevo consumo en la base de datos
    public boolean post(Consumo consumo) {
        boolean exito = false; //se declara la variable exito y se inicializa como false, indica si la operación fue exitosa

        try (Connection conn = ConexionBD.getConnection()) { //se abre la conexión a la base de datos utilizando try-with-resources
            String sql = "INSERT INTO consumos (id_reserva, id_producto, cantidad, subtotal) VALUES (?, ?, ?, ?)"; //se define la consulta SQL de inserción
            PreparedStatement stmt = conn.prepareStatement(sql); //se prepara la consulta SQL

            //se reemplazan los parámetros de la consulta con los valores del objeto consumo
            stmt.setInt(1, consumo.getId_reserva());
            stmt.setInt(2, consumo.getId_producto());
            stmt.setInt(3, consumo.getCantidad());
            stmt.setDouble(4, consumo.getSubtotal());

            int filas = stmt.executeUpdate(); //se ejecuta la consulta SQL y se obtiene el número de filas afectadas
            exito = filas > 0; //si filas es mayor que 0, se asigna true a la variable exito

        } catch (SQLException e) { //se captura cualquier excepción SQL
            e.printStackTrace(); //se imprime el error
        }

        return exito; //se retorna el valor de la variable exito
    }
    
    //método para obtener una lista de consumos por el ID de una reserva
    public List<Consumo> getByIdReserva(int idReserva) {
        List<Consumo> lista = new ArrayList<>(); //se crea una lista para almacenar los consumos obtenidos

        try (Connection conn = ConexionBD.getConnection()) { //se abre la conexión a la base de datos
            String sql = "SELECT id, id_reserva, id_producto, cantidad, subtotal FROM consumos WHERE id_reserva = ?"; //se define la consulta SQL
            PreparedStatement stmt = conn.prepareStatement(sql); //se prepara la consulta SQL
            stmt.setInt(1, idReserva); //se reemplaza el parámetro idReserva en la consulta
            ResultSet rs = stmt.executeQuery(); //se ejecuta la consulta SQL y se obtiene el resultado

            //se recorre el conjunto de resultados
            while (rs.next()) {
                //por cada fila se crea un objeto Consumo con los datos obtenidos
                Consumo consumo = new Consumo(
                    rs.getInt("id"),
                    rs.getInt("id_reserva"),
                    rs.getInt("id_producto"),
                    rs.getInt("cantidad"),
                    rs.getDouble("subtotal")
                );
                lista.add(consumo); //se agrega el objeto consumo a la lista
            }

        } catch (SQLException e) { //manejo de errores
            e.printStackTrace();
        }

        return lista; //se retorna la lista de consumos
    }
        
    //método para obtener un consumo específico por su ID
    public Consumo getById(int id) {
        Consumo consumo = null; //se declara la variable consumo e inicialmente es null

        try (Connection conn = ConexionBD.getConnection()) { //se abre la conexión a la base de datos
            String sql = "SELECT * FROM consumos WHERE id = ?"; //se define la consulta SQL
            PreparedStatement stmt = conn.prepareStatement(sql); //se prepara la consulta
            stmt.setInt(1, id); //se reemplaza el parámetro id en la consulta

            ResultSet rs = stmt.executeQuery(); //se ejecuta la consulta SQL

            if (rs.next()) { //si hay un resultado
                consumo = new Consumo(); //se crea un nuevo objeto Consumo
                consumo.setId(rs.getInt("id")); //se asigna el id del consumo
                consumo.setId_reserva(rs.getInt("id_reserva")); //se asigna el id de la reserva
                consumo.setId_producto(rs.getInt("id_producto")); //se asigna el id del producto
                consumo.setCantidad(rs.getInt("cantidad")); //se asigna la cantidad
                consumo.setSubtotal(rs.getDouble("subtotal")); //se asigna el subtotal
            }

        } catch (SQLException e) { //manejo de errores
            e.printStackTrace();
        }

        return consumo; //se retorna el objeto consumo, o null si no se encontró
    }
    
    //método para actualizar los datos de un consumo existente
    public boolean actualizarConsumo(Consumo consumo) {
        boolean actualizado = false; //se declara la variable actualizado y se inicializa como false

        try (Connection conn = ConexionBD.getConnection()) { //se abre la conexión a la base de datos
            String sql = "UPDATE consumos SET cantidad = ?, subtotal = ? WHERE id = ?"; //se define la consulta SQL
            PreparedStatement stmt = conn.prepareStatement(sql); //se prepara la consulta
            stmt.setInt(1, consumo.getCantidad()); //se asigna la cantidad a actualizar
            stmt.setDouble(2, consumo.getSubtotal()); //se asigna el subtotal a actualizar
            stmt.setInt(3, consumo.getId()); //se asigna el id del consumo que se actualizará

            actualizado = stmt.executeUpdate() > 0; //se ejecuta la consulta y se actualiza la variable actualizado si se modificó alguna fila
        } catch (SQLException e) { //manejo de errores
            e.printStackTrace();
        }

        return actualizado; //se retorna el valor de la variable actualizado
    }
    
    //método para eliminar un consumo por su ID
    public boolean del(int id) {
        boolean eliminado = false; //se declara la variable eliminado y se inicializa como false

        try (Connection conn = ConexionBD.getConnection()) { //se abre la conexión a la base de datos
            String sql = "DELETE FROM consumos WHERE id = ?"; //se define la consulta SQL
            PreparedStatement stmt = conn.prepareStatement(sql); //se prepara la consulta
            stmt.setInt(1, id); //se asigna el id del consumo que se eliminará

            int filasAfectadas = stmt.executeUpdate(); //se ejecuta la consulta y se obtiene el número de filas afectadas
            eliminado = filasAfectadas > 0; //si se eliminaron filas, la variable eliminado será true

        } catch (SQLException e) { //manejo de errores
            e.printStackTrace();
        }

        return eliminado; //se retorna el valor de la variable eliminado
    }
    
    //método para calcular el total de todos los consumos asociados a una reserva
    public double calcularTotalPorReserva(int idReserva) {
        double total = 0.0; //se declara la variable total y se inicializa en 0

        try (Connection conn = ConexionBD.getConnection()) { //se abre la conexión a la base de datos
            String sql = "SELECT SUM(subtotal) FROM consumos WHERE id_reserva = ?"; //se define la consulta SQL
            PreparedStatement stmt = conn.prepareStatement(sql); //se prepara la consulta
            stmt.setInt(1, idReserva); //se asigna el idReserva como parámetro

            ResultSet rs = stmt.executeQuery(); //se ejecuta la consulta
            if (rs.next()) { //si hay resultados
                total = rs.getDouble(1); //se obtiene el valor de la suma y se asigna a la variable total
            }

        } catch (SQLException e) { //manejo de errores
            e.printStackTrace();
        }

        return total; //se retorna el total calculado
    }
}
