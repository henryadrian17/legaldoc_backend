package dev.henrymolina.legaldoc_web_services.services.login.model.repository;

import dev.henrymolina.legaldoc_web_services.services.login.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}