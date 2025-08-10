package MODELO;

public class Pago {

    private int id; // identificador único del pago
    private int id_factura; // id de la factura asociada al pago
    private int id_metodo; // id del método de pago utilizado

    public Pago() {} // constructor vacío para crear el objeto sin inicializar atributos

    // constructor con parámetros para inicializar los atributos al crear el objeto
    public Pago(int id, int id_factura, int id_metodo) {
        this.id = id; // asigna el id recibido al atributo id
        this.id_factura = id_factura; // asigna el id de factura recibido al atributo correspondiente
        this.id_metodo = id_metodo; // asigna el id del método de pago recibido al atributo correspondiente
    }

    // getter para obtener el id del pago
    public int getId() {
        return id;
    }

    // setter para asignar un nuevo valor al id del pago
    public void setId(int id) {
        this.id = id;
    }

    // getter para obtener el id de la factura asociada
    public int getId_factura() {
        return id_factura;
    }

    // setter para asignar un nuevo id de factura
    public void setId_factura(int id_factura) {
        this.id_factura = id_factura;
    }

    // getter para obtener el id del método de pago
    public int getId_metodo() {
        return id_metodo;
    }

    // setter para asignar un nuevo id de método de pago
    public void setId_metodo(int id_metodo) {
        this.id_metodo = id_metodo;
    }
}
