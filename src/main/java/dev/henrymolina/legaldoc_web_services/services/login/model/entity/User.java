package dev.henrymolina.legaldoc_web_services.services.login.model.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;

@Entity
@Table(name = "user")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "last_name", nullable = false)
    private String apellido;

    @Column(name = "name", nullable = false)
    private String nombre;

    @Column(name = "create_at")
    private Timestamp fechaCreacion;

    @Column(name = "email", nullable = false)
    private String correo;

    @Column(name = "enabled")
    private Boolean habilitado;

    @Column(name = "bith", nullable = false)
    private LocalDate fechaNacimiento;

    @Column(name = "password", nullable = false)
    private String contrasena;

    @Column(name = "salt", nullable = false)
    private String salt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_type")
    private UserType userType;


}