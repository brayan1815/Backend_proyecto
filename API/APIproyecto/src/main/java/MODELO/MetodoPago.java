package MODELO;

public class MetodoPago {

    private int id; // identificador único del método de pago
    private String metodo_pago; // nombre o tipo del método de pago

    public MetodoPago() {} // constructor vacío, para crear un objeto sin inicializar atributos

    // constructor con parámetros para inicializar los atributos al crear el objeto
    public MetodoPago(int id, String metodo_pago) {
        this.id = id; // asigna el id recibido al atributo id
        this.metodo_pago = metodo_pago; // asigna el nombre recibido al atributo metodo_pago
    }

    // getter para obtener el valor del atributo id
    public int getId() {
        return id;
    }

    // setter para asignar un nuevo valor al atributo id
    public void setId(int id) {
        this.id = id;
    }

    // getter para obtener el nombre o tipo del método de pago
    public String getMetodoPago() {
        return metodo_pago;
    }

    // setter para asignar un nuevo nombre o tipo al método de pago
    public void setMetodoPago(String nombre) {
        this.metodo_pago = nombre;
    }
}
