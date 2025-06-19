/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODELO;

/**
 *
 * @author Brayan Estiven
 */
public class Usuario {
    private int id;    
    private int documento;
    private String nombre;
    private long telefono;
    private String correo;
    private String contrasenia;
    private int id_rol;
    
    
    public Usuario(){      
    }
    
    public Usuario( int id, int documento,String nombre,long telefono, String correo,String contrasenia, int id_rol) {
        this.id = id;
        this.documento = documento;
        this.nombre = nombre;
        this.telefono=telefono;
        this.correo=correo;
        this.contrasenia=contrasenia;
        this.id_rol=id_rol;
    }

    public int getId(){return id;}
    public void setId(int id){this.id=id;}
    
    public int getDocumento(){return documento;}
    public void setDocumento(int documento){this.documento=documento;}
    
    public String getNombre(){return nombre;}
    public void setNombre(String nombre){this.nombre=nombre;}
    
    public long getTelefono(){return telefono;}
    public void setTelefono(long telefono){this.telefono=telefono;}
    
    public String getCorreo(){return correo;}
    public void setCorreo(String correo){this.correo=correo;}
    
    public String getContrasenia(){return contrasenia;}
    public void setContrasenia(String contrasenia){this.contrasenia=contrasenia;}
    
    public int getId_rol(){return id_rol;}
    public void setId_rol(int id_rol){this.id_rol=id_rol;}
}
