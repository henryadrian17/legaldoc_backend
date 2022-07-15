package dev.henrymolina.legaldoc_web_services.services.model.mapper;

import dev.henrymolina.legaldoc_web_services.services.model.dto.UserServicesAndAsesor;
import dev.henrymolina.legaldoc_web_services.services.model.dto.UserServicesDto;
import dev.henrymolina.legaldoc_web_services.services.model.entity.UserServices;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface UserServicesMapper {
    UserServices userServicesDtoToUserServices(UserServicesDto userServicesDto);

    UserServicesDto userServicesToUserServicesDto(UserServices userServices);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    UserServices updateUserServicesFromUserServicesDto(UserServicesDto userServicesDto, @MappingTarget UserServices userServices);

    List<UserServicesDto> toDtoList(List<UserServices> byUserId);

    UserServices userServicesAndAsesorToUserServices(UserServicesAndAsesor userServicesAndAsesor);

    UserServicesAndAsesor userServicesToUserServicesAndAsesor(UserServices userServices);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    UserServices updateUserServicesFromUserServicesAndAsesor(UserServicesAndAsesor userServicesAndAsesor, @MappingTarget UserServices userServices);
}
