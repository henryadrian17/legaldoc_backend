package dev.henrymolina.legaldoc_web_services.services.model.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "user_services")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserServices {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String nombreServicio;

    @Column(name = "description", nullable = false)
    private String descriptionServicio;

    @Column(name = "price", nullable = false)
    private Double precioServicio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User userId;

    @Column( name = "enable", columnDefinition = "boolean default true")
    private Boolean habilitado;
}
