package MODELO;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

public class Reserva {
    // atributos que representan las propiedades de una reserva
    private int id; // identificador único de la reserva
    private int id_usuario; // id del usuario que realizó la reserva
    private int id_consola; // id de la consola reservada
    private int id_estado_reserva; // id que representa el estado de la reserva (pendiente, confirmada, cancelada, etc.)

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") // formato para serializar y deserializar las fechas en JSON
    private LocalDateTime hora_inicio; // fecha y hora de inicio de la reserva

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") // formato para serializar y deserializar las fechas en JSON
    private LocalDateTime hora_finalizacion; // fecha y hora de finalización de la reserva

    public Reserva() {} // constructor vacío para crear un objeto sin inicializar atributos

    // constructor con parámetros para inicializar los atributos al crear el objeto
    public Reserva(int id, int id_usuario, int id_consola, int id_estado_reserva, LocalDateTime hora_inicio, LocalDateTime hora_finalizacion) {
        this.id = id; // asigna el id recibido al atributo id
        this.id_usuario = id_usuario; // asigna el id de usuario al atributo id_usuario
        this.id_consola = id_consola; // asigna el id de consola al atributo id_consola
        this.id_estado_reserva = id_estado_reserva; // asigna el id del estado de reserva
        this.hora_inicio = hora_inicio; // asigna la fecha y hora de inicio
        this.hora_finalizacion = hora_finalizacion; // asigna la fecha y hora de finalización
    }

    // getter para obtener el id de la reserva
    public int getId() {
        return id;
    }

    // setter para asignar un nuevo id a la reserva
    public void setId(int id) {
        this.id = id;
    }

    // getter para obtener el id del usuario que hizo la reserva
    public int getId_usuario() {
        return id_usuario;
    }

    // setter para asignar un nuevo id de usuario a la reserva
    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    // getter para obtener el id de la consola reservada
    public int getId_consola() {
        return id_consola;
    }

    // setter para asignar un nuevo id de consola a la reserva
    public void setId_consola(int id_consola) {
        this.id_consola = id_consola;
    }

    // getter para obtener el estado actual de la reserva
    public int getId_estado_reserva() {
        return id_estado_reserva;
    }

    // setter para asignar un nuevo estado a la reserva
    public void setId_estado_reserva(int id_estado_reserva) {
        this.id_estado_reserva = id_estado_reserva;
    }

    // getter para obtener la fecha y hora de inicio de la reserva
    public LocalDateTime getHora_inicio() {
        return hora_inicio;
    }

    // setter para asignar una nueva fecha y hora de inicio a la reserva
    public void setHora_inicio(LocalDateTime hora_inicio) {
        this.hora_inicio = hora_inicio;
    }

    // getter para obtener la fecha y hora de finalización de la reserva
    public LocalDateTime getHora_finalizacion() {
        return hora_finalizacion;
    }

    // setter para asignar una nueva fecha y hora de finalización a la reserva
    public void setHora_finalizacion(LocalDateTime hora_finalizacion) {
        this.hora_finalizacion = hora_finalizacion;
    }
}
