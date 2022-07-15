package dev.henrymolina.legaldoc_web_services.services.model.dto;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

@Data

public class UserPublicData implements Serializable {
    private final Long idUser;
    private final String apellido;
    private final String nombre;
    private String tockenDeAcceso;
    private Timestamp fechaExpiracionTocken;
}
