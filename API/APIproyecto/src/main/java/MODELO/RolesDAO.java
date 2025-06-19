/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODELO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Brayan Estiven
 */
public class RolesDAO {
        public List<Rol> get(){
        List<Rol> lista = new ArrayList<>();
        
        try(Connection conn = ConexionBD.getConnection()){
            String sql="SELECT id,rol FROM roles";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()){
                Rol r = new Rol(
                    rs.getInt("id"),
                    rs.getString("rol")
                );
                lista.add(r);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return lista;
    }
}
