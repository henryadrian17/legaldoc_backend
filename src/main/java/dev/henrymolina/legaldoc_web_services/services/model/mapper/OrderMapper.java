package dev.henrymolina.legaldoc_web_services.services.model.mapper;

import dev.henrymolina.legaldoc_web_services.services.model.dto.DetallesOrdenDto;
import dev.henrymolina.legaldoc_web_services.services.model.dto.OrderAndDetailsDto;
import dev.henrymolina.legaldoc_web_services.services.model.dto.OrderDto;
import dev.henrymolina.legaldoc_web_services.services.model.entity.Order;
import dev.henrymolina.legaldoc_web_services.services.model.entity.OrderByUserDto;
import dev.henrymolina.legaldoc_web_services.services.model.entity.OrderDetails.DetallesOrden;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface OrderMapper {
    Order orderDtoToOrder(OrderDto orderDto);

    OrderDto orderToOrderDto(Order order);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Order updateOrderFromOrderDto(OrderDto orderDto, @MappingTarget Order order);

    DetallesOrden detallesOrdenDtoToDetallesOrden(DetallesOrdenDto detallesOrdenDto);

    DetallesOrdenDto detallesOrdenToDetallesOrdenDto(DetallesOrden detallesOrden);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    DetallesOrden updateDetallesOrdenFromDetallesOrdenDto(DetallesOrdenDto detallesOrdenDto, @MappingTarget DetallesOrden detallesOrden);

    Order orderAndDetailsDtoToOrder(OrderAndDetailsDto orderAndDetailsDto);

    OrderAndDetailsDto orderToOrderAndDetailsDto(Order order);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Order updateOrderFromOrderAndDetailsDto(OrderAndDetailsDto orderAndDetailsDto, @MappingTarget Order order);

    @Mapping(source = "fechaCreacionOrden", target = "fechaCreacion")
    @Mapping(source = "fechaActualizacionOrden", target = "fechaActualizacion")
    @Mapping(source = "montoOrden", target = "monto")
    @Mapping(source = "monedaPago", target = "moneda")
    @Mapping(source = "estadoPago", target = "estado")
    Order orderByUserDtoToOrder(OrderByUserDto orderByUserDto);

    @InheritInverseConfiguration(name = "orderByUserDtoToOrder")
    OrderByUserDto orderToOrderByUserDto(Order order);

    @InheritConfiguration(name = "orderByUserDtoToOrder")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Order updateOrderFromOrderByUserDto(OrderByUserDto orderByUserDto, @MappingTarget Order order);
}
