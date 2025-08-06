
package MODELO;


public class Tipo {
    private int id;
    private String tipo;
    double precio_hora;
    public int id_estado_tipo;

    public Tipo() {}

    public Tipo(int id, String tipo, double precio_hora,int id_estado_tipo) {
        this.id = id;
        this.tipo = tipo;
        this.precio_hora = precio_hora;
        this.id_estado_tipo=id_estado_tipo;
        
    }
    

    public int getId() {return id;}
    public void setId(int id) {this.id = id;}

    public String getTipo() {return tipo;}
    public void setTipo(String tipo) {this.tipo = tipo;}

    public double getPrecio_hora() {return precio_hora;}
    public void setPrecio_hora(double precio_hora) {this.precio_hora = precio_hora;}
    
    public int getId_estado_tipo(){return id_estado_tipo;}
    public void set_id_estado_tipo(int id_estado_tipo){this.id_estado_tipo=id_estado_tipo;}
}
