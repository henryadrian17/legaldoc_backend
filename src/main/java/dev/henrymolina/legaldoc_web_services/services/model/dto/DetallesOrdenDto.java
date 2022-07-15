package dev.henrymolina.legaldoc_web_services.services.model.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class DetallesOrdenDto implements Serializable {
    private final Double precioAtual;
    private final String estadoServicio;
}
