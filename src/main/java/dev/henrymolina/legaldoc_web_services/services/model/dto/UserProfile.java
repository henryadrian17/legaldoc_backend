package dev.henrymolina.legaldoc_web_services.services.model.dto;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDate;

@Data
public class UserProfile implements Serializable {
    private final Long id;
    private final String fotoPerfil;
    private final String apellidoUsuario;
    private final String nombreUsuario;
    private final String descripcionUsuario;
    private final Timestamp fechaCreacionCuenta;
    private final String correo;
    private final LocalDate fechaNacimiento;
}
