package MODELO;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

public class Reserva {
    private int id;
    private int id_usuario;
    private int id_consola;
    private int id_estado_reserva;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")//se indica que la hora sera en este formato
    private LocalDateTime hora_inicio; 

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime hora_finalizacion;

    public Reserva() {}

    public Reserva(int id, int id_usuario, int id_consola, int id_estado_reserva,LocalDateTime hora_inicio, LocalDateTime hora_finalizacion) {
        this.id = id;
        this.id_usuario = id_usuario;
        this.id_consola = id_consola;
        this.id_estado_reserva = id_estado_reserva;
        this.hora_inicio = hora_inicio;
        this.hora_finalizacion = hora_finalizacion;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getId_usuario() { return id_usuario; }
    public void setId_usuario(int id_usuario) { this.id_usuario = id_usuario; }

    public int getId_consola() { return id_consola; }
    public void setId_consola(int id_consola) { this.id_consola = id_consola; }

    public int getId_estado_reserva() { return id_estado_reserva; }
    public void setId_estado_reserva(int id_estado_reserva) { this.id_estado_reserva = id_estado_reserva; }

    public LocalDateTime getHora_inicio() { return hora_inicio; }
    public void setHora_inicio(LocalDateTime hora_inicio) { this.hora_inicio = hora_inicio; }

    public LocalDateTime getHora_finalizacion() { return hora_finalizacion; }
    public void setHora_finalizacion(LocalDateTime hora_finalizacion) { this.hora_finalizacion = hora_finalizacion; }
}
