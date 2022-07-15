package dev.henrymolina.legaldoc_web_services.services.model.repository;

import dev.henrymolina.legaldoc_web_services.services.model.entity.User;
import dev.henrymolina.legaldoc_web_services.services.model.entity.UserType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByCorreoAndHabilitadoIsTrue(String correo);

    Page<User> findByTipoUsuarioInAndHabilitadoTrue(Collection<UserType> tipoUsuarios, Pageable pageable);


    List<User> findByTockenDeAcceso(String token);

    //@Query("SELECT u FROM User u WHERE u.habilitado = true AND u.idUser = ?1")
    User findByIdUserAndHabilitadoIsTrue(Long id);


}