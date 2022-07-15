package dev.henrymolina.legaldoc_web_services.services.model.repository;

import dev.henrymolina.legaldoc_web_services.services.model.entity.Order;
import dev.henrymolina.legaldoc_web_services.services.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("select o from Order o where o.userId.idUser = ?1")
    List<Order> findOrdersByUserId(Long idUser);

}