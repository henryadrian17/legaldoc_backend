package dev.henrymolina.legaldoc_web_services.services.model.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@Data
public class UserDto implements Serializable {
    @NotNull(message = "El campo nombre no puede ser nulo")
    @NotEmpty(message = "El campo nombre no puede ser vacio")
    private final String nombre;

    @NotNull(message = "El campo apellido no puede ser nulo")
    @NotEmpty(message = "El campo apellido no puede ser vacio")
    private final String apellido;

    @Email(message = "El correo no es valido")
    @NotNull(message = "El campo correo no puede ser nulo")
    @NotEmpty(message = "El campo correo no puede ser vacio")
    private final String correo;

    @NotNull(message = "La fecha de cumpleaños no puede ser nulo")
    private final LocalDate fechaNacimiento;

    @NotNull(message = "La contraseña no puede ser nula")
    @NotEmpty(message = "La contraseña no puede estar vacia")
    private final String contrasena;

    @NotEmpty(message = "El campo de confirmacionde contraseña no puede ser vacio")
    @NotNull(message = "El campo de confirmacionde contraseña no puede ser nulo")
    private String contrasenaConfirmacion;
}
