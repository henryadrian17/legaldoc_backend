package dev.henrymolina.legaldoc_web_services.services.login.model.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data

public class UserPublicData implements Serializable {
    private final String apellido;
    private final String nombre;
    private final String correo;
    private List permisos;
}
