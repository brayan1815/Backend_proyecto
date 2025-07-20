package MODELO;


public class MetodoPago {
    private int id;
    private String metodo_pago;

    public MetodoPago() {}

    public MetodoPago(int id, String metodo_pago) {
        this.id = id;
        this.metodo_pago = metodo_pago;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getMetodoPago() { return metodo_pago; }
    public void setMetodoPago(String nombre) { this.metodo_pago = nombre; }
}
