package dev.henrymolina.legaldoc_web_services.services.model.entity;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
public class OrderByUserDto implements Serializable {
    private final Long id;
    private final Timestamp fechaCreacionOrden;
    private final Timestamp fechaActualizacionOrden;
    private final Double montoOrden;
    private final String monedaPago;
    private final String estadoPago;
    private final String emailPagador;
    private final String nombrePagador;
    private final String paisPagador;
    private Long totalServicios;
    private String metodoPago;
}
