package dev.henrymolina.legaldoc_web_services.services.login.model.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "user_type")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String nombreTipoUsuario;

    @Column(name = "description")
    private String descripcionTipoUsuario = "";

    @Column(name = "create_at")
    private Timestamp fechaCreacion = new Timestamp(System.currentTimeMillis());

    @Column(name = "enabled")
    private Boolean habilitado = true;

}