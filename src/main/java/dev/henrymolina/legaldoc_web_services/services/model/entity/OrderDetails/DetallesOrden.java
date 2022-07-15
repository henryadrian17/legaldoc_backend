package dev.henrymolina.legaldoc_web_services.services.model.entity.OrderDetails;

import dev.henrymolina.legaldoc_web_services.services.model.entity.Order;
import dev.henrymolina.legaldoc_web_services.services.model.entity.UserServices;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "detalles_orden")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DetallesOrden {
    @EmbeddedId
    private DetallesOrdenId id;

    @MapsId("idServicio")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_servicio", nullable = false)
    private UserServices idServicio;

    @MapsId("idOrden")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_orden", nullable = false)
    private Order idOrden;

    @Column(name = "current_price")
    private Double precioAtual;

    @Column(name = "service_status", length = 30)
    private String estadoServicio;


}