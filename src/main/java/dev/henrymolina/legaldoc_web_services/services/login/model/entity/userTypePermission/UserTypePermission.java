package dev.henrymolina.legaldoc_web_services.services.login.model.entity.userTypePermission;

import dev.henrymolina.legaldoc_web_services.services.login.model.entity.Permisions;
import dev.henrymolina.legaldoc_web_services.services.login.model.entity.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "user_type_permissions")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserTypePermission {
    @EmbeddedId
    private UserTypePermissionId id;

    @MapsId("idPermissions")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_permissions", nullable = false)
    private Permisions idPermiso;

    @MapsId("idUserType")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_user_type", nullable = false)
    private UserType idTipoUsuario;

    @Column(name = "enable")
    private Boolean habilitado = true;

}