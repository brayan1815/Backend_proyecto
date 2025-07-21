
package MODELO;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;


public class ReservaDTO {
    private int id;
    private long documentoUsuario;
    private String nombreUsuario;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime horaInicio;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime horaFinalizacion;
    private String nombreConsola;
    private int idEstadoReserva;
    
    // Constructor vacío
    public ReservaDTO() {
    }

    // Constructor con todos los campos
    public ReservaDTO(int id, long documentoUsuario, String nombreUsuario, LocalDateTime horaInicio, LocalDateTime horaFinalizacion, String nombreConsola,int idEstadoReserva) {
        this.id = id;
        this.documentoUsuario = documentoUsuario;
        this.nombreUsuario = nombreUsuario;
        this.horaInicio = horaInicio;
        this.horaFinalizacion = horaFinalizacion;
        this.nombreConsola = nombreConsola;
        this.idEstadoReserva = idEstadoReserva;
    }

    // Getters y Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getDocumentoUsuario() {
        return documentoUsuario;
    }

    public void setDocumentoUsuario(long documentoUsuario) {
        this.documentoUsuario = documentoUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public LocalDateTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalDateTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public LocalDateTime getHoraFinalizacion() {
        return horaFinalizacion;
    }

    public void setHoraFinalizacion(LocalDateTime horaFinalizacion) {
        this.horaFinalizacion = horaFinalizacion;
    }

    public String getNombreConsola() {
        return nombreConsola;
    }

    public void setNombreConsola(String nombreConsola) {
        this.nombreConsola = nombreConsola;
    }
    
     public int getIdEstadoReserva() {
        return idEstadoReserva;
    }

    public void setIdEstadoReserva(int idEstadoReserva) {
        this.idEstadoReserva = idEstadoReserva;
    }
}
