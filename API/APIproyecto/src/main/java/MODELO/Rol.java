/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODELO;



public class Rol {
    
    private int id;
    private String rol;
    
    public Rol(){}
    
    public Rol( int id, String rol) {
        this.id = id;
        this.rol = rol;;
    }
    
    public int getId(){return id;}
    public void setId(int id){this.id=id;}
    
    public String getRol(){return rol;}
    public void setRol(String rol){this.rol=rol;}
    
    
}
