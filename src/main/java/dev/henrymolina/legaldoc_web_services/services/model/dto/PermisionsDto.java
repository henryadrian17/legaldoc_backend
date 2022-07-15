package dev.henrymolina.legaldoc_web_services.services.model.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class PermisionsDto implements Serializable {
    private final Long id;
    private final String nombrePermiso;
    private final String descripcionPermiso;
}
