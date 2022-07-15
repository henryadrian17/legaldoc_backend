package dev.henrymolina.legaldoc_web_services.services.model.entity;

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
    private Long idUser;

    @Column(name = "foto", nullable = true)
    private String foto;

    @Column(name = "last_name", nullable = false)
    private String apellido;

    @Column(name = "name", nullable = false)
    private String nombre;

    @Column(name = "about", nullable = true)
    private String descripcionUsuario;

    @Column(name = "create_at")
    private Timestamp fechaCreacion;

    @Column(name = "email", nullable = false)
    private String correo;

    @Column(name = "enabled", columnDefinition = "boolean default true")
    private Boolean habilitado;

    @Column(name = "bith", nullable = false)
    private LocalDate fechaNacimiento;

    @Column(name = "password", nullable = false)
    private String contrasena;

    @Column(name = "salt", nullable = false)
    private String salt;

    @Column(name = "tocken_access", nullable = false)
    private String tockenDeAcceso;

    @Column(name = "tocken_expiration_date", nullable = false)
    private Timestamp fechaExpiracionTocken;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_type")
    private UserType tipoUsuario;


}