package MODELO; //se define el paquete MODELO donde estará contenida esta clase

//se define la clase consola que define una consola de videojuegos en este caso
public class Consola {

    //se declara la variable id que almacenará el identificador único de la consola
    private int id;
    //se declara la variable numero_serie que almacenará el número de serie de la consola
    private String numero_serie;
    //se declara la variable nombre que almacenará el nombre de la consola
    private String nombre;
    //se declara la variable descripcion que almacenará la descripción de la consola
    private String descripcion;
    //se declara la variable id_tipo que almacenará el identificador del tipo de consola
    private int id_tipo;
    //se declara la variable id_estado que almacenará el identificador del estado de la consola
    private int id_estado;
    //se declara la variable id_imagen que almacenará el identificador de la imagen asociada a la consola
    private int id_imagen;

    //constructor vacío, se usa cuando no se quiere inicializar la consola con valores de entrada
    public Consola() {}

    //constructor con todos los atributos, se usa cuando se quiere crear una consola con todos sus datos
    public Consola(int id, String nombre, String descripcion, int id_tipo, int id_estado, int id_imagen, String numero_serie) {
        this.id = id; //se asigna el valor del parámetro id al atributo id de la clase
        this.numero_serie = numero_serie; //se asigna el valor del parámetro numero_serie al atributo numero_serie de la clase
        this.nombre = nombre; //se asigna el valor del parámetro nombre al atributo nombre de la clase
        this.descripcion = descripcion; //se asigna el valor del parámetro descripcion al atributo descripcion de la clase
        this.id_tipo = id_tipo; //se asigna el valor del parámetro id_tipo al atributo id_tipo de la clase
        this.id_estado = id_estado; //se asigna el valor del parámetro id_estado al atributo id_estado de la clase
        this.id_imagen = id_imagen; //se asigna el valor del parámetro id_imagen al atributo id_imagen de la clase
    }

    //método getter que retorna el valor del atributo id
    public int getId() { return id; }

    //método setter que asigna un valor al atributo id
    public void setId(int id) { this.id = id; }
    
    //método getter que retorna el valor del atributo numero_serie
    public String getNumero_serie(){ return numero_serie; }

    //método setter que asigna un valor al atributo numero_serie
    public void setNumero_serie(String numero_serie){ this.numero_serie = numero_serie; }

    //método getter que retorna el valor del atributo nombre
    public String getNombre() { return nombre; }

    //método setter que asigna un valor al atributo nombre
    public void setNombre(String nombre) { this.nombre = nombre; }

    //método getter que retorna el valor del atributo descripcion
    public String getDescripcion() { return descripcion; }

    //método setter que asigna un valor al atributo descripcion
    public void setDescripcion(String decripcion) { this.descripcion = decripcion; }

    //método getter que retorna el valor del atributo id_tipo
    public int getId_tipo() { return id_tipo; }

    //método setter que asigna un valor al atributo id_tipo
    public void setId_tipo(int id_tipo) { this.id_tipo = id_tipo; }

    //método getter que retorna el valor del atributo id_estado
    public int getId_estado() { return id_estado; }

    //método setter que asigna un valor al atributo id_estado
    public void setId_estado(int id_estado) { this.id_estado = id_estado; }

    //método getter que retorna el valor del atributo id_imagen
    public int getId_imagen() { return id_imagen; }

    //método setter que asigna un valor al atributo id_imagen
    public void setId_imagen(int id_imagen) { this.id_imagen = id_imagen; }
}
