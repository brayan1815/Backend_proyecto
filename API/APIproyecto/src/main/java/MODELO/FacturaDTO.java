package MODELO;

public class FacturaDTO {
    
    //atributos que representan los datos del DTO
    private int id;
    private long minutosConsumidos;
    private double totalTiempo;
    private double totalProductos;
    private double totalGeneral;

    public FacturaDTO() {}//constructor vacio

    //constructor con toods los datos
    public FacturaDTO(int id,long minutosConsumidos, double totalTiempo, double totalProductos, double totalGeneral) {
        this.id=id;
        this.minutosConsumidos = minutosConsumidos;
        this.totalTiempo = totalTiempo;
        this.totalProductos = totalProductos;
        this.totalGeneral = totalGeneral;
    }
    
    //getters y setters
    public int getId(){
        return id;
    }
    
    public void setId(int id){
        this.id=id;
    }

    public long getMinutosConsumidos() {
        return minutosConsumidos;
    }

    public void setMinutosConsumidos(long minutosConsumidos) {
        this.minutosConsumidos = minutosConsumidos;
    }

    public double getTotalTiempo() {
        return totalTiempo;
    }

    public void setTotalTiempo(double totalTiempo) {
        this.totalTiempo = totalTiempo;
    }

    public double getTotalProductos() {
        return totalProductos;
    }

    public void setTotalProductos(double totalProductos) {
        this.totalProductos = totalProductos;
    }

    public double getTotalGeneral() {
        return totalGeneral;
    }

    public void setTotalGeneral(double totalGeneral) {
        this.totalGeneral = totalGeneral;
    }
}
