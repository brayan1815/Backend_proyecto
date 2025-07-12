
package MODELO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.Response;



public class UsuariosDAO {
    //se crea el metodo que se encargara de obtener todos los usuarios de la base de datos
    public List<Usuario> get(){
        //se crea una lista vacia para almacenar los usuarios
        List<Usuario> lista = new ArrayList<>();
        
        
        try(Connection conn = ConexionBD.getConnection()){//se oobtiene la conexion a la base de datos usando 
                                                          //la clase conexionBD
                                                          
            //se define la consulta SQL para obtener los usuarios
            String sql="SELECT id,documento,nombre,telefono,correo,contrasenia,id_rol FROM usuarios";
            PreparedStatement stmt = conn.prepareStatement(sql);//se prepara la consulta SQL
            ResultSet rs = stmt.executeQuery();//se ejecuta la conuslta y se almacenan los resultados en la variable rs
            
            while(rs.next()){//se recorre cada fila del resultado
                Usuario u = new Usuario(//se crea un objeto usuario con los datos de la fila
                    rs.getInt("id"),
                    rs.getLong("documento"),
                    rs.getString("nombre"),
                    rs.getLong("telefono"),
                    rs.getString("correo"),
                    rs.getString("contrasenia"),
                    rs.getInt("id_rol")
                );
                //se agrega a la lista el usuario
                lista.add(u);
            }
        }catch(SQLException e){
            e.printStackTrace();//si ocurre un error se imprime en la consola
        }
        return lista;//se retorna con los usuarios
    }
    
    public Usuario getById(int id) {
        Usuario usuario = null;
        try (Connection conn = ConexionBD.getConnection()) {
            String sql = "SELECT id, documento, nombre, telefono, correo, contrasenia, id_rol FROM usuarios WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                usuario = new Usuario(
                    rs.getInt("id"),
                    rs.getInt("documento"),
                    rs.getString("nombre"),
                    rs.getLong("telefono"),
                    rs.getString("correo"),
                    rs.getString("contrasenia"),
                    rs.getInt("id_rol")
                );
            }
        } catch (SQLException e) 
        {
            e.printStackTrace();
        }
        return usuario;
    }
    
    public Usuario getByCorreo(String correo){
        Usuario usuario=null; //se crea la variable usuario y se inicializa como null
        
        try(Connection conn=ConexionBD.getConnection()){//se instancia el objeto de la clase conexionBD y se hace
                                                        //referencia al metodo getConnection
            String sql="SELECT id, documento, nombre, telefono, correo, contrasenia, id_rol FROM usuarios WHERE correo = ?";//se hace la consulta sql
            PreparedStatement stmt = conn.prepareStatement(sql);//se prepara la consulta SQL
            stmt.setString(1, correo);//se envia el correo que se recibe como parametro a la consulta
            ResultSet rs = stmt.executeQuery();//se ejecuta la consulta y se almacena el resultado en la variable rs
            
            if(rs.next()){ //se verifica que la consulta devuelva por lo menos un resultado y se procede a leerlos
                usuario = new Usuario( //se crea el objeto usuario con los valores obtenidos de la base de datos
                    rs.getInt("id"),
                    rs.getInt("documento"),
                    rs.getString("nombre"),
                    rs.getLong("telefono"),
                    rs.getString("correo"),
                    rs.getString("contrasenia"),
                    rs.getInt("id_rol")
                );
            }
        }catch (SQLException e){
            e.printStackTrace();//si ocurre un error se imprime en lac consola
        }
        return usuario;//se retorna el objeto usuario, si no hay ninguno se retorna null
    }
    
    public Usuario getByDocumento(long documento) {
        Usuario usuario = null;

        try (Connection conn = ConexionBD.getConnection()) {
            String sql = "SELECT * FROM usuarios WHERE documento = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setLong(1, documento);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setDocumento(rs.getLong("documento"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setTelefono(rs.getLong("telefono"));
                usuario.setCorreo(rs.getString("correo"));
                usuario.setContrasenia(rs.getString("contrasenia"));
                usuario.setId_rol(rs.getInt("id_rol"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return usuario;
    }

    
    public boolean post(Usuario usuario) {
        try (Connection conn = ConexionBD.getConnection()) {
            String sql = "INSERT INTO usuarios (documento, nombre, telefono, correo, contrasenia, id_rol) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setLong(1, usuario.getDocumento());
            stmt.setString(2, usuario.getNombre());
            stmt.setLong(3, usuario.getTelefono());
            stmt.setString(4, usuario.getCorreo());
            stmt.setString(5, usuario.getContrasenia());
            stmt.setInt(6, usuario.getId_rol());

            int filas = stmt.executeUpdate();
            return filas > 0;
        } catch (SQLException e) 
        {
            e.printStackTrace();
            return false;
        }   
    }
    
    public boolean put(int id, Usuario usuario) {
        String sql = "UPDATE usuarios SET documento = ?, nombre = ?, telefono = ?, correo = ?, contrasenia = ?, id_rol = ? WHERE id = ?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, usuario.getDocumento());
            stmt.setString(2, usuario.getNombre());
            stmt.setLong(3, usuario.getTelefono());
            stmt.setString(4, usuario.getCorreo());
            stmt.setString(5, usuario.getContrasenia());
            stmt.setInt(6, usuario.getId_rol());
            stmt.setInt(7, id);

            int filas = stmt.executeUpdate();
            return filas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean delete(int id) {
        String sql = "DELETE FROM usuarios WHERE id = ?";
        try (Connection conn = ConexionBD.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
        
            stmt.setInt(1, id);
            int filas = stmt.executeUpdate();
            return filas > 0;      
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    
    

}
