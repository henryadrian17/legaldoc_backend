package dev.henrymolina.legaldoc_web_services.services.model.repository;

import dev.henrymolina.legaldoc_web_services.services.model.entity.Permisions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PermisionsRepository extends JpaRepository<Permisions, Long> {
    @Query(value = "select p from User u join UserTypePermission utp on utp.idTipoUsuario = u.tipoUsuario.id join UserType ut on ut.id = utp.idTipoUsuario join Permisions p on p.id = utp.idPermiso WHERE utp.habilitado is true and p.habilitado is true and u.correo = ?1")
    List<Permisions> findByUserPermisions(String correo);

}