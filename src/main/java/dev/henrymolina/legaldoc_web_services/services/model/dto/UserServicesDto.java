package dev.henrymolina.legaldoc_web_services.services.model.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserServicesDto implements Serializable {
    private final Long id;
    private final String nombreServicio;
    private final String descriptionServicio;
    private final Double precioServicio;
}
