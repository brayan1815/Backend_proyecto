package MODELO; //se define el paquete MODELO donde estará contenida esta clase

//se define la clase Consumo que representa el registro de un consumo realizado
public class Consumo {

    //se declara la variable id que almacenará el identificador único del consumo
    private int id;
    //se declara la variable id_reserva que almacenará el identificador de la reserva asociada al consumo
    private int id_reserva;
    //se declara la variable id_producto que almacenará el identificador del producto consumido
    private int id_producto;
    //se declara la variable cantidad que almacenará la cantidad del producto consumido
    private int cantidad;
    //se declara la variable subtotal que almacenará el valor total del consumo según cantidad y precio unitario
    private double subtotal;

    //constructor vacío, se usa cuando no se quiere inicializar el consumo con valores de entrada
    public Consumo() {}

    //constructor con todos los atributos, se usa cuando se quiere crear un consumo con todos sus datos
    public Consumo(int id, int id_reserva, int id_producto, int cantidad, double subtotal) {
        this.id = id; //se asigna el valor del parámetro id al atributo id de la clase
        this.id_reserva = id_reserva; //se asigna el valor del parámetro id_reserva al atributo id_reserva de la clase
        this.id_producto = id_producto; //se asigna el valor del parámetro id_producto al atributo id_producto de la clase
        this.cantidad = cantidad; //se asigna el valor del parámetro cantidad al atributo cantidad de la clase
        this.subtotal = subtotal; //se asigna el valor del parámetro subtotal al atributo subtotal de la clase
    }

    //método getter que retorna el valor del atributo id
    public int getId() { return id; }

    //método setter que asigna un valor al atributo id
    public void setId(int id) { this.id = id; }

    //método getter que retorna el valor del atributo id_reserva
    public int getId_reserva() { return id_reserva; }

    //método setter que asigna un valor al atributo id_reserva
    public void setId_reserva(int id_reserva) { this.id_reserva = id_reserva; }

    //método getter que retorna el valor del atributo id_producto
    public int getId_producto() { return id_producto; }

    //método setter que asigna un valor al atributo id_producto
    public void setId_producto(int id_producto) { this.id_producto = id_producto; }

    //método getter que retorna el valor del atributo cantidad
    public int getCantidad() { return cantidad; }

    //método setter que asigna un valor al atributo cantidad
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    //método getter que retorna el valor del atributo subtotal
    public double getSubtotal() { return subtotal; }

    //método setter que asigna un valor al atributo subtotal
    public void setSubtotal(double subtotal) { this.subtotal = subtotal; }
}
