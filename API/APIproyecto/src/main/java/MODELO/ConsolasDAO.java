
package MODELO;

import BD.ConexionBD;//se importa la clase que maneja la conexion a la base de datos
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ConsolasDAO {
    public List<Consola> getAll() {//se crea el metodo getAll, esye metodo retorna una lista con todas las consolas de la
                                   //base de datos
        
        List<Consola> consolas = new ArrayList<>();//se crea la lista que almacenara las consolas

        try (Connection conn = ConexionBD.getConnection()) {//se abre la conexion a la base de datos
            String sql = "SELECT * FROM consolas";//se crea la consulta SQL
            PreparedStatement stmt = conn.prepareStatement(sql);//se prepara la consulta
            ResultSet rs = stmt.executeQuery();//se ejecuta la consulta

            while (rs.next()) {
                //se recorre cada una de las filas y se arma el objeto consola
                Consola consola = new Consola();
                consola.setId(rs.getInt("id"));
                consola.setNombre(rs.getString("nombre"));
                consola.setDescripcion(rs.getString("descripcion"));
                consola.setId_tipo(rs.getInt("id_tipo"));
                consola.setId_estado(rs.getInt("id_estado"));
                consola.setId_imagen(rs.getInt("id_imagen"));

                consolas.add(consola);//el objeto consola se agrega a la lista
            }

        } catch (Exception e) {
            e.printStackTrace();//se manejan los errores, si ocurre uno se imprima
        }

        return consolas;//se retorna la lista consolas
    }
    
    public Consola getById(int id) {//se crea el metodo getById, este metodo retornara una consola especifica segun su id
        Consola consola = null;//se crea la variable consola y se inicializa como null

        try (Connection conn = ConexionBD.getConnection()) {//se abre la conexion a la base de datos
            String sql = "SELECT * FROM consolas WHERE id = ?";//se crea la consulta SQL
            PreparedStatement stmt = conn.prepareStatement(sql);//se prepara la consulra
            stmt.setInt(1, id);//se cambian los parametros de la consulta

            ResultSet rs = stmt.executeQuery();//se ejecuta la consulta y se almacena en la ariable rs

            if (rs.next()) {
                //si se encoentro un registro se arma el objeto
                consola = new Consola();
                consola.setId(rs.getInt("id"));
                consola.setNombre(rs.getString("nombre"));
                consola.setDescripcion(rs.getString("descripcion"));
                consola.setId_tipo(rs.getInt("id_tipo"));
                consola.setId_estado(rs.getInt("id_estado"));
                consola.setId_imagen(rs.getInt("id_imagen"));
            }

        } catch (SQLException e) {
            e.printStackTrace();//si ocurre un error se imprime
        }

        return consola;//se retorna la consola, si no se encontro nada se retorna null
    }
    
    public boolean post(Consola consola) {
        //se crea el metodo que guaradara una nueva consola en la base de datos
        boolean exito = false;//se declara la variable exito y se inicializa como false

        try (Connection conn = ConexionBD.getConnection()) {//se abre la conexion a la base de datos
            //se crea la consulta SQL
            String sql = "INSERT INTO consolas (nombre, descripcion, id_tipo, id_estado, id_imagen) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);//se prepara la consulta
            
            //se reemplazan los paramtros de la comsulta
            stmt.setString(1, consola.getNombre());
            stmt.setString(2, consola.getDescripcion());
            stmt.setInt(3, consola.getId_tipo());
            stmt.setInt(4, consola.getId_estado());
            stmt.setInt(5, consola.getId_imagen());

            int filas = stmt.executeUpdate();//se ejecuta la consulta
            exito = filas > 0;//si se inserta al menos una fila exito es true

        } catch (SQLException e) {
            e.printStackTrace();//se imprimen los errores
        }

        return exito;//se retorna la variable exito, que es true o false
    }
    
    public boolean put(Consola consola) {
        //se crea el metodo para actualizar una consola existente
        boolean exito = false;//se declara la variable exito y se inicializa como false

        try (Connection conn = ConexionBD.getConnection()) {//se abre la conexion a la base de datos
            //se crea la consulta SQL
            String sql = "UPDATE consolas SET nombre = ?, descripcion = ?, id_tipo = ?, id_estado = ?, id_imagen = ? WHERE id = ?";
            //se prepara la conuslta sql
            PreparedStatement stmt = conn.prepareStatement(sql);
            
            //se reemplazan los valores enviados en la consulta
            stmt.setString(1, consola.getNombre());
            stmt.setString(2, consola.getDescripcion());
            stmt.setInt(3, consola.getId_tipo());
            stmt.setInt(4, consola.getId_estado());
            stmt.setInt(5, consola.getId_imagen());
            stmt.setInt(6, consola.getId());

            int filas = stmt.executeUpdate();//se ejecuta la consulta
            exito = filas > 0;//si se afecto mas de una fila se exito es true

        } catch (SQLException e) {
            e.printStackTrace();//si ocurre un error se imprime 
        }

        return exito;//se retorna l avariable exito
    }
    
    public boolean Delete(int id) {
        //se crea el metodo que va a eliminar una consola exixtente
        boolean eliminado = false;//se declara la variable eliminado y se inicializa como false

        try (Connection conn = ConexionBD.getConnection()) {//se abre la conexion a la base de datos
            String sql = "DELETE FROM consolas WHERE id = ?";//se crea la consulta SQL
            PreparedStatement stmt = conn.prepareStatement(sql);//se prepara la consulta
            stmt.setInt(1, id);//se reemplazan los parametros de la consulta

            int filas = stmt.executeUpdate();//se ejecuta la consulta
            eliminado = filas > 0;//si se afecto mas de  filas eliminado es true

        } catch (SQLException e) {
            e.printStackTrace();//si ocurre un error se imprime
        }

        return eliminado;//se retorna la variable eliminado
    }
}
