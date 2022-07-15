package dev.henrymolina.legaldoc_web_services.services.model.mapper;

import dev.henrymolina.legaldoc_web_services.services.model.dto.*;
import dev.henrymolina.legaldoc_web_services.services.model.entity.User;
import dev.henrymolina.legaldoc_web_services.services.model.dto.UserProfile;
import dev.henrymolina.legaldoc_web_services.services.model.dto.UserUpdatePassword;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface UserMapper {
    User userDtoToUser(UserDto userDto);

    UserDto userToUserDto(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User updateUserFromUserDto(UserDto userDto, @MappingTarget User user);

    User userLoginToUser(UserLogin userLogin);

    UserLogin userToUserLogin(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User updateUserFromUserLogin(UserLogin userLogin, @MappingTarget User user);

    User userPublicDataToUser(UserPublicData userPublicData);

    UserPublicData userToUserPublicData(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User updateUserFromUserPublicData(UserPublicData userPublicData, @MappingTarget User user);

    User userAsesorToUser(UserAsesor userAsesor);

    UserAsesor userToUserAsesor(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User updateUserFromUserAsesor(UserAsesor userAsesor, @MappingTarget User user);

    User userAsesorResumenToUser(UserAsesorResumen userAsesorResumen);

    UserAsesorResumen userToUserAsesorResumen(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User updateUserFromUserAsesorResumen(UserAsesorResumen userAsesorResumen, @MappingTarget User user);

    @Mapping(source = "id", target = "idUser")
    @Mapping(source = "fotoPerfil", target = "foto")
    @Mapping(source = "apellidoUsuario", target = "apellido")
    @Mapping(source = "nombreUsuario", target = "nombre")
    @Mapping(source = "fechaCreacionCuenta", target = "fechaCreacion")
    User userProfileToUser(UserProfile userProfile);

    @InheritInverseConfiguration(name = "userProfileToUser")
    UserProfile userToUserProfile(User user);

    @InheritConfiguration(name = "userProfileToUser")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User updateUserFromUserProfile(UserProfile userProfile, @MappingTarget User user);

    User userUpdatePasswordToUser(UserUpdatePassword userUpdatePassword);

    UserUpdatePassword userToUserUpdatePassword(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User updateUserFromUserUpdatePassword(UserUpdatePassword userUpdatePassword, @MappingTarget User user);
}
