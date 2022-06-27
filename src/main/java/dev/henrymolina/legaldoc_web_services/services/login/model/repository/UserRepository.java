package dev.henrymolina.legaldoc_web_services.services.login.model.repository;

import dev.henrymolina.legaldoc_web_services.services.login.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByCorreoAndHabilitadoIsTrue(String correo);

}