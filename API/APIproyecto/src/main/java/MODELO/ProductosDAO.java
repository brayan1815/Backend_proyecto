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

public class ProductosDAO {
    public boolean post(Producto producto) {
        boolean exito = false;

        try (Connection conn = ConexionBD.getConnection()) {
            String sql = "INSERT INTO productos (nombre, descripcion, precio, cantidades_disponibles, id_estado_producto, id_imagen) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, producto.getNombre());
            stmt.setString(2, producto.getDescripcion());
            stmt.setDouble(3, producto.getPrecio());
            stmt.setInt(4, producto.getCantidades_disponibles());
            stmt.setInt(5, producto.getId_estado_producto());
            stmt.setInt(6, producto.getId_imagen());

            int filas = stmt.executeUpdate();
            exito = filas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return exito;
    }
     public List<Producto> getAll() {
        List<Producto> lista = new ArrayList<>();

        try (Connection conn = ConexionBD.getConnection()) {
            String sql = "SELECT * FROM productos";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Producto p = new Producto();
                p.setId(rs.getInt("id"));
                p.setNombre(rs.getString("nombre"));
                p.setDescripcion(rs.getString("descripcion"));
                p.setPrecio(rs.getDouble("precio"));
                p.setCantidades_disponibles(rs.getInt("cantidades_disponibles"));
                p.setId_estado_producto(rs.getInt("id_estado_producto"));
                p.setId_imagen(rs.getInt("id_imagen"));
                lista.add(p);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }
     
    public Producto getById(int id) {
    Producto producto = null;

    try (Connection conn = ConexionBD.getConnection()) {
        String sql = "SELECT * FROM productos WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);

        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            producto = new Producto();
            producto.setId(rs.getInt("id"));
            producto.setNombre(rs.getString("nombre"));
            producto.setDescripcion(rs.getString("descripcion"));
            producto.setPrecio(rs.getDouble("precio"));
            producto.setCantidades_disponibles(rs.getInt("cantidades_disponibles"));
            producto.setId_estado_producto(rs.getInt("id_estado_producto"));
            producto.setId_imagen(rs.getInt("id_imagen"));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return producto;
}
    
    public boolean put(Producto producto) {
        boolean actualizado = false;

        try (Connection conn = ConexionBD.getConnection()) {
            String sql = "UPDATE productos SET nombre = ?, descripcion = ?, precio = ?, cantidades_disponibles = ?, id_estado_producto = ?, id_imagen = ? WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, producto.getNombre());
            stmt.setString(2, producto.getDescripcion());
            stmt.setDouble(3, producto.getPrecio());
            stmt.setInt(4, producto.getCantidades_disponibles());
            stmt.setInt(5, producto.getId_estado_producto());
            stmt.setInt(6, producto.getId_imagen());
            stmt.setInt(7, producto.getId());

            actualizado = stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return actualizado;
    }
    
    public boolean Delete(int id) {
        boolean eliminado = false;

        try (Connection conn = ConexionBD.getConnection()) {
            String sql = "DELETE FROM productos WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);

            int filas = stmt.executeUpdate();
            eliminado = filas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return eliminado;
    }

}
