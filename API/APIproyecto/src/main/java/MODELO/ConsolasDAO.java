package MODELO;

//se importa la clase que maneja la conexion a la base de datos
import BD.ConexionBD;
import java.sql.Connection;//se importa la clase que representa la conexión a la base de datos
import java.sql.PreparedStatement;//se importa la clase que permite preparar consultas SQL con parámetros
import java.sql.ResultSet;//se importa la clase que permite manejar los resultados de las consultas
import java.sql.SQLException;//se importa la clase para manejar errores relacionados con SQL
import java.util.ArrayList;//se importa la clase para manejar listas dinámicas
import java.util.List;//se importa la interfaz List para manejar colecciones de datos

//se define la clase ConsolasDAO, que contiene los métodos para interactuar con la tabla consolas en la base de datos
public class ConsolasDAO {
    
    public List<Consola> getAll() {
        //se crea el metodo getAll, este metodo retorna una lista con todas las consolas de la base de datos
        
        List<Consola> consolas = new ArrayList<>();//se crea la lista que almacenara las consolas

        try (Connection conn = ConexionBD.getConnection()) {//se abre la conexión a la base de datos
            String sql = "SELECT * FROM consolas";//se crea la consulta SQL para obtener todas las consolas
            PreparedStatement stmt = conn.prepareStatement(sql);//se prepara la consulta
            ResultSet rs = stmt.executeQuery();//se ejecuta la consulta y se almacena el resultado en rs

            while (rs.next()) {//se recorre cada registro obtenido
                //se crea un objeto consola y se asignan sus valores con los datos obtenidos
                Consola consola = new Consola();
                consola.setId(rs.getInt("id"));
                consola.setNumero_serie(rs.getString("numero_serie"));
                consola.setNombre(rs.getString("nombre"));
                consola.setDescripcion(rs.getString("descripcion"));
                consola.setId_tipo(rs.getInt("id_tipo"));
                consola.setId_estado(rs.getInt("id_estado"));
                consola.setId_imagen(rs.getInt("id_imagen"));

                consolas.add(consola);//se agrega el objeto consola a la lista
            }

        } catch (Exception e) {
            e.printStackTrace();//si ocurre un error se imprime
        }

        return consolas;//se retorna la lista de consolas
    }
    
    public List<ConsolaDTO> getAllConPrecio() {
        //se crea el metodo getAllConPrecio, este retorna todas las consolas junto con su precio por hora
        
        List<ConsolaDTO> consolasDTO = new ArrayList<>();//se crea la lista que almacenará las consolas con precio

        try (Connection conn = ConexionBD.getConnection()) {//se abre la conexión a la base de datos
            //se crea la consulta SQL con INNER JOIN para obtener también el precio de cada consola
            String sql = "SELECT \n" +
                        "    c.id AS id,\n" +
                        "    c.numero_serie AS numero_serie,\n" +
                        "    c.nombre AS nombre,\n" +
                        "    c.descripcion AS descripcion,\n" +
                        "    t.precio_hora AS precio_hora,\n" +
                        "    c.id_estado AS id_estado,\n" +
                        "    c.id_imagen AS id_imagen\n" +
                        "FROM \n" +
                        "    consolas c\n" +
                        "INNER JOIN \n" +
                        "    tipos t ON c.id_tipo = t.id;";
            
            PreparedStatement stmt = conn.prepareStatement(sql);//se prepara la consulta
            ResultSet rs = stmt.executeQuery();//se ejecuta la consulta y se almacena el resultado en rs

            while (rs.next()) {//se recorre cada registro obtenido
                //se crea un objeto ConsolaDTO y se asignan sus valores con los datos obtenidos
                ConsolaDTO consola = new ConsolaDTO();
                consola.setId(rs.getInt("id"));
                consola.setNumero_serie(rs.getString("numero_serie"));
                consola.setNombre(rs.getString("nombre"));
                consola.setDescripcion(rs.getString("descripcion"));
                consola.setPrecioHora(rs.getDouble("precio_hora"));
                consola.setIdEstado(rs.getInt("id_estado"));
                consola.setIdImagen(rs.getInt("id_imagen"));

                consolasDTO.add(consola);//se agrega el objeto a la lista
            }

        } catch (Exception e) {
            e.printStackTrace();//si ocurre un error se imprime
        }

        return consolasDTO;//se retorna la lista con consolas y su precio
    }
    
    public Consola getById(int id) {
        //se crea el metodo getById, este retorna una consola específica según su id
        Consola consola = null;//se inicializa como null, en caso de no encontrar coincidencias

        try (Connection conn = ConexionBD.getConnection()) {//se abre la conexión a la base de datos
            String sql = "SELECT * FROM consolas WHERE id = ?";//se crea la consulta SQL con parámetro
            PreparedStatement stmt = conn.prepareStatement(sql);//se prepara la consulta
            stmt.setInt(1, id);//se reemplaza el parámetro con el valor de id

            ResultSet rs = stmt.executeQuery();//se ejecuta la consulta y se guarda en rs

            if (rs.next()) {//si se encontró un registro
                consola = new Consola();//se crea el objeto consola
                consola.setId(rs.getInt("id"));
                consola.setNumero_serie(rs.getString("numero_serie"));
                consola.setNombre(rs.getString("nombre"));
                consola.setDescripcion(rs.getString("descripcion"));
                consola.setId_tipo(rs.getInt("id_tipo"));
                consola.setId_estado(rs.getInt("id_estado"));
                consola.setId_imagen(rs.getInt("id_imagen"));
            }

        } catch (SQLException e) {
            e.printStackTrace();//si ocurre un error se imprime
        }

        return consola;//se retorna la consola o null si no se encontró
    }
    
    public List<Consola> getByIdTipo(int idTipo) {
        // se crea el método getByIdTipo, retorna una lista de consolas según el id_tipo
        List<Consola> consolas = new ArrayList<>();

        try (Connection conn = ConexionBD.getConnection()) { // se abre la conexión a la base de datos
            String sql = "SELECT * FROM consolas WHERE id_tipo = ?"; // consulta SQL con parámetro
            PreparedStatement stmt = conn.prepareStatement(sql); // se prepara la consulta
            stmt.setInt(1, idTipo); // se reemplaza el parámetro con el valor de idTipo

            ResultSet rs = stmt.executeQuery(); // se ejecuta la consulta

            while (rs.next()) { // mientras haya registros
                Consola consola = new Consola(); // se crea un objeto consola
                consola.setId(rs.getInt("id"));
                consola.setNumero_serie(rs.getString("numero_serie"));
                consola.setNombre(rs.getString("nombre"));
                consola.setDescripcion(rs.getString("descripcion"));
                consola.setId_tipo(rs.getInt("id_tipo"));
                consola.setId_estado(rs.getInt("id_estado"));
                consola.setId_imagen(rs.getInt("id_imagen"));

                consolas.add(consola); // se agrega la consola a la lista
            }

        } catch (SQLException e) {
            e.printStackTrace(); // si ocurre un error se imprime
        }

        return consolas; // se retorna la lista de consolas
    }

    
    public Consola getByNumeroSerie(String numero) {
        //se crea el metodo getByNumeroSerie, este retorna una consola específica según su número de serie
        Consola consola = null;//se inicializa como null, en caso de no encontrar coincidencias

        try (Connection conn = ConexionBD.getConnection()) {//se abre la conexión a la base de datos
            String sql = "SELECT * FROM consolas WHERE numero_serie=?";//se crea la consulta SQL con parámetro
            PreparedStatement stmt = conn.prepareStatement(sql);//se prepara la consulta
            stmt.setString(1, numero);//se reemplaza el parámetro con el número de serie

            ResultSet rs = stmt.executeQuery();//se ejecuta la consulta

            if (rs.next()) {//si se encontró un registro
                consola = new Consola();//se crea el objeto consola
                consola.setId(rs.getInt("id"));
                consola.setNumero_serie(rs.getString("numero_serie"));
                consola.setNombre(rs.getString("nombre"));
                consola.setDescripcion(rs.getString("descripcion"));
                consola.setId_tipo(rs.getInt("id_tipo"));
                consola.setId_estado(rs.getInt("id_estado"));
                consola.setId_imagen(rs.getInt("id_imagen"));
            }

        } catch (SQLException e) {
            e.printStackTrace();//si ocurre un error se imprime
        }

        return consola;//se retorna la consola o null si no se encontró
    }
    
    public boolean post(Consola consola) {
        //se crea el metodo post para guardar una nueva consola en la base de datos
        boolean exito = false;//se inicializa en false, cambiará a true si la operación es exitosa

        try (Connection conn = ConexionBD.getConnection()) {//se abre la conexión a la base de datos
            String sql = "INSERT INTO consolas (numero_serie,nombre, descripcion, id_tipo, id_estado, id_imagen) VALUES (?, ?, ?, ?, ?,?)";
            PreparedStatement stmt = conn.prepareStatement(sql);//se prepara la consulta
            
            //se reemplazan los parámetros con los valores de la consola
            stmt.setString(1, consola.getNumero_serie());
            stmt.setString(2, consola.getNombre());
            stmt.setString(3, consola.getDescripcion());
            stmt.setInt(4, consola.getId_tipo());
            stmt.setInt(5, consola.getId_estado());
            stmt.setInt(6, consola.getId_imagen());

            int filas = stmt.executeUpdate();//se ejecuta la consulta
            exito = filas > 0;//si se insertó al menos una fila, exito será true

        } catch (SQLException e) {
            e.printStackTrace();//si ocurre un error se imprime
        }

        return exito;//se retorna true o false según el resultado
    }
    
    public boolean put(Consola consola) {
        //se crea el metodo put para actualizar una consola existente
        boolean exito = false;//se inicializa en false

        try (Connection conn = ConexionBD.getConnection()) {//se abre la conexión a la base de datos
            String sql = "UPDATE consolas SET numero_serie=?, nombre = ?, descripcion = ?, id_tipo = ?, id_estado = ?, id_imagen = ? WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);//se prepara la consulta
            
            //se reemplazan los valores de la consola en la consulta
            stmt.setString(1, consola.getNumero_serie());
            stmt.setString(2, consola.getNombre());
            stmt.setString(3, consola.getDescripcion());
            stmt.setInt(4, consola.getId_tipo());
            stmt.setInt(5, consola.getId_estado());
            stmt.setInt(6, consola.getId_imagen());
            stmt.setInt(7, consola.getId());

            int filas = stmt.executeUpdate();//se ejecuta la consulta
            exito = filas > 0;//si se afectó al menos una fila, exito será true

        } catch (SQLException e) {
            e.printStackTrace();//si ocurre un error se imprime 
        }

        return exito;//se retorna true o false
    }
    
    public boolean Delete(int id) {
        //se crea el metodo Delete para eliminar una consola existente
        boolean eliminado = false;//se inicializa en false

        try (Connection conn = ConexionBD.getConnection()) {//se abre la conexión a la base de datos
            String sql = "DELETE FROM consolas WHERE id = ?";//se crea la consulta SQL con parámetro
            PreparedStatement stmt = conn.prepareStatement(sql);//se prepara la consulta
            stmt.setInt(1, id);//se reemplaza el parámetro con el id recibido

            int filas = stmt.executeUpdate();//se ejecuta la consulta
            eliminado = filas > 0;//si se afectó al menos una fila, eliminado será true

        } catch (SQLException e) {
            e.printStackTrace();//si ocurre un error se imprime
        }

        return eliminado;//se retorna true o false
    }
}
