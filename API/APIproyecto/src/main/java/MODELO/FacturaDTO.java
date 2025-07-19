package MODELO;

public class FacturaDTO {
    private long minutosConsumidos;
    private double totalTiempo;
    private double totalProductos;
    private double totalGeneral;

    public FacturaDTO() {}

    public FacturaDTO(long minutosConsumidos, double totalTiempo, double totalProductos, double totalGeneral) {
        this.minutosConsumidos = minutosConsumidos;
        this.totalTiempo = totalTiempo;
        this.totalProductos = totalProductos;
        this.totalGeneral = totalGeneral;
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
