package dev.henrymolina.legaldoc_web_services.services.model.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserAsesorResumen implements Serializable {
    private final Long idUser;
    private final String apellido;
    private final String nombre;
    private final String correo;
}
