package dev.henrymolina.legaldoc_web_services.services.login.model.mapper;

import dev.henrymolina.legaldoc_web_services.services.login.model.dto.UserDto;
import dev.henrymolina.legaldoc_web_services.services.login.model.dto.UserLogin;
import dev.henrymolina.legaldoc_web_services.services.login.model.dto.UserPublicData;
import dev.henrymolina.legaldoc_web_services.services.login.model.entity.User;
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
}
