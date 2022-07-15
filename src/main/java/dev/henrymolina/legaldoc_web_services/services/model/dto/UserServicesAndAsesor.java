package dev.henrymolina.legaldoc_web_services.services.model.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserServicesAndAsesor implements Serializable {
    private final Long id;
    private final String nombreServicio;
    private final String descriptionServicio;
    private String estado;
    private UserAsesorResumen asesor;
}
