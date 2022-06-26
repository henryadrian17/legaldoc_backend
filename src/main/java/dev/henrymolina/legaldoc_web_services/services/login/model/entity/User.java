package dev.henrymolina.legaldoc_web_services.services.login.model.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.time.Instant;
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
    private Instant fechaCreacion = Instant.now();

    @Column(name = "email", nullable = false, unique = true)
    @Email(message = "El email no es valido")
    private String correo;

    @Column(name = "enabled")
    private Boolean habilitado = true;

    @Column(name = "bith", nullable = false)
    private LocalDate fechaNacimiento;

    @Column(name = "password", nullable = false)
    private String contrasena;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_type")
    private UserType userType;

}