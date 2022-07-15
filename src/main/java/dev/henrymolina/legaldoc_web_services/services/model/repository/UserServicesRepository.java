package dev.henrymolina.legaldoc_web_services.services.model.repository;

import dev.henrymolina.legaldoc_web_services.services.model.entity.UserServices;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface UserServicesRepository extends JpaRepository<UserServices, Long> {
    List<UserServices> findByUserId_IdUserAndHabilitadoIsTrue(Long id);

    Page<UserServices> findByIdInAndHabilitadoTrue(Collection<Long> itemsId, Pageable pageable);


    UserServices findByIdAndHabilitadoTrue(Long idServicios);

    List<UserServices> findByIdIn(List<UserServices> collect);
}