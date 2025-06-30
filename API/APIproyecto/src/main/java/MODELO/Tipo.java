
package MODELO;


public class Tipo {
    private int id;
    private String tipo;
    private double precio_hora;

    public Tipo() {}

    public Tipo(int id, String tipo, double precio_hora) {
        this.id = id;
        this.tipo = tipo;
        this.precio_hora = precio_hora;
    }
    

    public int getId() {return id;}
    public void setId(int id) {this.id = id;}

    public String getTipo() {return tipo;}
    public void setTipo(String tipo) {this.tipo = tipo;}

    public double getPrecio_hora() {return precio_hora;}
    public void setPrecio_hora(double precio_hora) {this.precio_hora = precio_hora;}
}
