package dev.henrymolina.legaldoc_web_services.services.model.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class UserAsesor implements Serializable {
    private final Long idUser;
    private final String apellido;
    private final String nombre;
    private final String correo;
    private String foto;
    private final String descripcionUsuario;
    private List<UserServicesDto> servicios;
}
