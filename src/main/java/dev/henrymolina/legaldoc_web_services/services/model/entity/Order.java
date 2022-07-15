package dev.henrymolina.legaldoc_web_services.services.model.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "user_orden")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "paypal_id", nullable = false)
    private String idPaypal;

    @Column(name = "create_time", nullable = false)
    private Timestamp fechaCreacion;

    @Column(name = "update_time", nullable = false)
    private Timestamp fechaActualizacion;

    @Column(name = "amount", nullable = false)
    private Double monto;

    @Column(name = "currency", nullable = false)
    private String moneda;

    @Column(name = "status_orden", nullable = false)
    private String estado;

    @Column(name = "payer_email", nullable = false)
    private String emailPagador;

    @Column(name = "payer_name", nullable = false)
    private String nombrePagador;

    @Column(name = "payer_id", nullable = false)
    private String idPagador;

    @Column(name = "payer_country", nullable = false)
    private String paisPagador;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User userId;
}