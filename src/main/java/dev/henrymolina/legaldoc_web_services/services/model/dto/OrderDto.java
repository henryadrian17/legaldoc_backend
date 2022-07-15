package dev.henrymolina.legaldoc_web_services.services.model.dto;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

@Data
public class OrderDto implements Serializable {
    private final String idPaypal;
    private final Timestamp fechaCreacion;
    private final Timestamp fechaActualizacion;
    private final Double monto;
    private final String moneda;
    private final String estado;
    private final String emailPagador;
    private final String nombrePagador;
    private final String idPagador;
    private final String paisPagador;
    private List<Long> idServicios;
}
