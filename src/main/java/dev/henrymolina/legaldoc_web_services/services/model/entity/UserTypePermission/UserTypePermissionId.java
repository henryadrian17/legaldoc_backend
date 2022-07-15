package dev.henrymolina.legaldoc_web_services.services.model.entity.UserTypePermission;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
public class UserTypePermissionId implements Serializable {
    private static final long serialVersionUID = 6322494083495223209L;
    @Column(name = "id_permissions", nullable = false)
    private Long idPermissions;

    @Column(name = "id_user_type", nullable = false)
    private Long idUserType;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        UserTypePermissionId entity = (UserTypePermissionId) o;
        return Objects.equals(this.idUserType, entity.idUserType) &&
                Objects.equals(this.idPermissions, entity.idPermissions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUserType, idPermissions);
    }

}