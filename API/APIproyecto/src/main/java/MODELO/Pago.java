
package MODELO;


public class Pago {
    private int id;
    private int id_factura;
    private int id_metodo;

    // Constructor vacío
    public Pago() {}

    // Constructor con parámetros
    public Pago(int id, int id_factura, int id_metodo) {
        this.id = id;
        this.id_factura = id_factura;
        this.id_metodo = id_metodo;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_factura() {
        return id_factura;
    }

    public void setId_factura(int id_factura) {
        this.id_factura = id_factura;
    }

    public int getId_metodo() {
        return id_metodo;
    }

    public void setId_metodo(int id_metodo) {
        this.id_metodo = id_metodo;
    }
}

