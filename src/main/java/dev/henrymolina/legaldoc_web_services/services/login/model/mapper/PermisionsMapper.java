package dev.henrymolina.legaldoc_web_services.services.login.model.mapper;

import dev.henrymolina.legaldoc_web_services.services.login.model.entity.Permisions;
import dev.henrymolina.legaldoc_web_services.services.login.model.entity.PermisionsDto;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface PermisionsMapper {
    Permisions permisionsDtoToPermisions(PermisionsDto permisionsDto);

    PermisionsDto permisionsToPermisionsDto(Permisions permisions);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Permisions updatePermisionsFromPermisionsDto(PermisionsDto permisionsDto, @MappingTarget Permisions permisions);

    List<PermisionsDto> toDtoList(List<Permisions> byUserPermisions);
}
