package dev.henrymolina.legaldoc_web_services.services.model.entity.OrderDetails;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DetallesOrdenId implements Serializable {
    private static final long serialVersionUID = 2873819284621727308L;
    @Column(name = "id_servicio", nullable = false)
    private Long idServicio;

    @Column(name = "id_orden", nullable = false)
    private Long idOrden;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        DetallesOrdenId entity = (DetallesOrdenId) o;
        return Objects.equals(this.idServicio, entity.idServicio) &&
                Objects.equals(this.idOrden, entity.idOrden);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idServicio, idOrden);
    }

}