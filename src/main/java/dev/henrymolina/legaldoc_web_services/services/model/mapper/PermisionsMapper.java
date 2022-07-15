package dev.henrymolina.legaldoc_web_services.services.model.mapper;

import dev.henrymolina.legaldoc_web_services.services.model.dto.PermisionsDto;
import dev.henrymolina.legaldoc_web_services.services.model.entity.Permisions;
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
