package MODELO;


public class MetodoPago {
    //se declaran los atributos de la clase
    private int id;
    private String metodo_pago;

    public MetodoPago() {}//cosntructor vacio

    //cinstructor con datos
    public MetodoPago(int id, String metodo_pago) {
        this.id = id;
        this.metodo_pago = metodo_pago;
    }

    //getters y setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getMetodoPago() { return metodo_pago; }
    public void setMetodoPago(String nombre) { this.metodo_pago = nombre; }
}
