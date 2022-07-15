package dev.henrymolina.legaldoc_web_services.services.model.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class UserUpdatePassword implements Serializable {
    private String contrasenaActual;
    private String contrasena;
    private String contrasenaConfirmacion;

    public boolean isValid() {
        if (contrasena == null || contrasenaConfirmacion == null || contrasenaActual == null) {
            return false;
        } else if (contrasena.isEmpty() || contrasenaConfirmacion.isEmpty()|| contrasenaActual.isEmpty()) {
            return false;
        } else if (!contrasena.equals(contrasenaConfirmacion)) {
            return false;
        } else {
            return true;
        }

    }

    public String getErrorMessage() {
        if (contrasena == null || contrasenaConfirmacion == null || contrasenaActual == null) {
            return "El campo contraseña no puede ser nulo";
        } else if (contrasena.isEmpty() || contrasenaConfirmacion.isEmpty() || contrasenaActual.isEmpty()) {
            return "El campo contraseña no puede ser vacio";
        } else if (!contrasena.equals(contrasenaConfirmacion)) {
            return "Las contraseñas no coinciden";
        } else {
            return "";
        }
    }
}
