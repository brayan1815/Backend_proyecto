package MODELO; //se define el paquete MODELO donde estará contenida esta clase

//se define la clase ConsolaDTO que representará un objeto de transferencia de datos de una consola
public class ConsolaDTO {

    //se declara la variable id que almacenará el identificador único de la consola
    private int id;
    //se declara la variable numero_serie que almacenará el número de serie de la consola
    private String numero_serie;
    //se declara la variable nombre que almacenará el nombre de la consola
    private String nombre;
    //se declara la variable descripcion que almacenará la descripción de la consola
    private String descripcion;
    //se declara la variable precioHora que almacenará el precio por hora del uso de la consola
    private double precioHora; 
    //se declara la variable idEstado que almacenará el identificador del estado de la consola
    private int idEstado;
    //se declara la variable idImagen que almacenará el identificador de la imagen asociada a la consola
    private int idImagen;
    
    //constructor vacío, se usa cuando no se quiere inicializar la consola con valores de entrada
    public ConsolaDTO() {
        //constructor sin parámetros, se deja vacío intencionalmente
    }

    //constructor con todos los atributos, se usa cuando se quiere crear una consola con todos sus datos
    public ConsolaDTO(int id, String numero_serie, String nombre, String descripcion, double precioHora, int idEstado, int idImagen) {
        this.id = id; //se asigna el valor del parámetro id al atributo id de la clase
        this.numero_serie = numero_serie; //se asigna el valor del parámetro numero_serie al atributo numero_serie de la clase
        this.nombre = nombre; //se asigna el valor del parámetro nombre al atributo nombre de la clase
        this.descripcion = descripcion; //se asigna el valor del parámetro descripcion al atributo descripcion de la clase
        this.precioHora = precioHora; //se asigna el valor del parámetro precioHora al atributo precioHora de la clase
        this.idEstado = idEstado; //se asigna el valor del parámetro idEstado al atributo idEstado de la clase
        this.idImagen = idImagen; //se asigna el valor del parámetro idImagen al atributo idImagen de la clase
    }

    //método getter que retorna el valor del atributo id
    public int getId() {
        return id;
    }

    //método setter que asigna un valor al atributo id
    public void setId(int id) {
        this.id = id;
    }
    
    //método getter que retorna el valor del atributo numero_serie
    public String getNumero_serie() { return numero_serie; }

    //método setter que asigna un valor al atributo numero_serie
    public void setNumero_serie(String numero_serie) { this.numero_serie = numero_serie; }

    //método getter que retorna el valor del atributo nombre
    public String getNombre() {
        return nombre;
    }

    //método setter que asigna un valor al atributo nombre
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    //método getter que retorna el valor del atributo descripcion
    public String getDescripcion() {
        return descripcion;
    }

    //método setter que asigna un valor al atributo descripcion
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    //método getter que retorna el valor del atributo precioHora
    public double getPrecioHora() {
        return precioHora;
    }

    //método setter que asigna un valor al atributo precioHora
    public void setPrecioHora(double precioHora) {
        this.precioHora = precioHora;
    }

    //método getter que retorna el valor del atributo idEstado
    public int getIdEstado() {
        return idEstado;
    }

    //método setter que asigna un valor al atributo idEstado
    public void setIdEstado(int idEstado) {
        this.idEstado = idEstado;
    }

    //método getter que retorna el valor del atributo idImagen
    public int getIdImagen() {
        return idImagen;
    }

    //método setter que asigna un valor al atributo idImagen
    public void setIdImagen(int idImagen) {
        this.idImagen = idImagen;
    }
}
