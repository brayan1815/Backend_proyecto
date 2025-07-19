
package MODELO;

public class Factura {
    private int id;
    private int idReserva;
    private double total;
    

    public Factura() {}


    public Factura(int id, int idReserva, double total) {
        this.id = id;
        this.idReserva = idReserva;
        this.total = total;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(int idReserva) {
        this.idReserva = idReserva;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
