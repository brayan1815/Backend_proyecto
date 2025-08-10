package MODELO;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

public class ReservaDTO {
    // atributos que representan las propiedades de una reserva con información adicional
    private int id; // identificador único de la reserva
    private long documentoUsuario; // documento o identificación del usuario que hizo la reserva
    private String nombreUsuario; // nombre del usuario que hizo la reserva

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") // formato para serializar/deserializar la fecha en JSON
    private LocalDateTime horaInicio; // fecha y hora de inicio de la reserva

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") // formato para serializar/deserializar la fecha en JSON
    private LocalDateTime horaFinalizacion; // fecha y hora de finalización de la reserva

    private String nombreConsola; // nombre de la consola reservada
    private int idEstadoReserva; // identificador del estado de la reserva (activo, cancelado, etc.)

    // constructor vacío para crear un objeto sin inicializar atributos
    public ReservaDTO() {
    }

    // constructor con todos los parámetros para inicializar el objeto completo
    public ReservaDTO(int id, long documentoUsuario, String nombreUsuario, LocalDateTime horaInicio, LocalDateTime horaFinalizacion, String nombreConsola, int idEstadoReserva) {
        this.id = id; // asigna el id recibido al atributo id
        this.documentoUsuario = documentoUsuario; // asigna el documento del usuario
        this.nombreUsuario = nombreUsuario; // asigna el nombre del usuario
        this.horaInicio = horaInicio; // asigna la fecha y hora de inicio
        this.horaFinalizacion = horaFinalizacion; // asigna la fecha y hora de finalización
        this.nombreConsola = nombreConsola; // asigna el nombre de la consola
        this.idEstadoReserva = idEstadoReserva; // asigna el id del estado de la reserva
    }

    // getter para obtener el id de la reserva
    public int getId() {
        return id;
    }

    // setter para asignar un nuevo id a la reserva
    public void setId(int id) {
        this.id = id;
    }

    // getter para obtener el documento del usuario que hizo la reserva
    public long getDocumentoUsuario() {
        return documentoUsuario;
    }

    // setter para asignar un nuevo documento al usuario
    public void setDocumentoUsuario(long documentoUsuario) {
        this.documentoUsuario = documentoUsuario;
    }

    // getter para obtener el nombre del usuario que hizo la reserva
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    // setter para asignar un nuevo nombre al usuario
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    // getter para obtener la fecha y hora de inicio de la reserva
    public LocalDateTime getHoraInicio() {
        return horaInicio;
    }

    // setter para asignar una nueva fecha y hora de inicio
    public void setHoraInicio(LocalDateTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    // getter para obtener la fecha y hora de finalización de la reserva
    public LocalDateTime getHoraFinalizacion() {
        return horaFinalizacion;
    }

    // setter para asignar una nueva fecha y hora de finalización
    public void setHoraFinalizacion(LocalDateTime horaFinalizacion) {
        this.horaFinalizacion = horaFinalizacion;
    }

    // getter para obtener el nombre de la consola reservada
    public String getNombreConsola() {
        return nombreConsola;
    }

    // setter para asignar un nuevo nombre de consola
    public void setNombreConsola(String nombreConsola) {
        this.nombreConsola = nombreConsola;
    }

    // getter para obtener el estado de la reserva
    public int getIdEstadoReserva() {
        return idEstadoReserva;
    }

    // setter para asignar un nuevo estado a la reserva
    public void setIdEstadoReserva(int idEstadoReserva) {
        this.idEstadoReserva = idEstadoReserva;
    }
}
