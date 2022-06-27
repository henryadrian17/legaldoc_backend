package dev.henrymolina.legaldoc_web_services.services.login.model.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class UserLogin implements Serializable {
    @Email(message = "El correo no es valido")
    @NotNull(message = "El campo correo no puede ser nulo")
    @NotEmpty(message = "El campo correo no puede estar vacio")
    private final String correo;
    @NotNull(message = "La contraseña no puede ser nula")
    @NotEmpty(message = "La contraseña no puede estar vacia")
    private final String contrasena;
}
