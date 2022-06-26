package dev.henrymolina.legaldoc_web_services.services.login.model.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@Data
public class UserDto implements Serializable {
    @NotNull(message = "El campo apellido no puede ser nulo")
    @NotEmpty(message = "El campo apellido no puede ser vacio")
    private final String apellido;
    @NotNull(message = "El campo nombre no puede ser nulo")
    @NotEmpty(message = "El campo nombre no puede ser vacio")
    private final String nombre;
    @Email(message = "El email no es valido")
    @NotNull(message = "El campo email no puede ser nulo")
    @NotEmpty(message = "El campo email no puede ser vacio")
    private final String correo;
    @NotNull(message = "La fecha de cumpleaños no puede ser nulo")
    private final LocalDate fechaNacimiento;
    @NotNull(message = "El password no puede ser nulo")
    @NotEmpty(message = "El password no puede ser vacio")
    private final String contrasena;
}
