package dev.henrymolina.legaldoc_web_services.services.model.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class OrderAndDetailsDto implements Serializable {
    private final Long id;
    private final Double monto;
    private final String moneda;
    private final String estado;
    private List<UserServicesAndAsesor> detallesOrden;
}
