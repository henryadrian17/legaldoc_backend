package dev.henrymolina.legaldoc_web_services.services.model.repository;

import dev.henrymolina.legaldoc_web_services.services.model.entity.OrderDetails.DetallesOrden;
import dev.henrymolina.legaldoc_web_services.services.model.entity.OrderDetails.DetallesOrdenId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DetallesOrdenRepository extends JpaRepository<DetallesOrden, DetallesOrdenId> {
    List<DetallesOrden> findById_IdOrden(Long idOrden);

    Long countById_IdOrden(Long idOrden);


}