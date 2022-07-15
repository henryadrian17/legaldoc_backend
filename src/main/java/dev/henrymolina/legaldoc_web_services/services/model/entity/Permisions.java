package dev.henrymolina.legaldoc_web_services.services.model.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "permisions")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Permisions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String nombrePermiso;

    @Column(name = "description")
    private String descripcionPermiso = "";

    @Column(name = "create_at")
    private Timestamp fechaCreacion = new Timestamp(System.currentTimeMillis());

    @Column(name = "enabled", columnDefinition = "boolean default true")
    private Boolean habilitado = true;

}