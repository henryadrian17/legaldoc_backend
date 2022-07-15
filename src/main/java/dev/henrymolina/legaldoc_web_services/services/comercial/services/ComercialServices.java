package dev.henrymolina.legaldoc_web_services.services.comercial.services;

import dev.henrymolina.legaldoc_web_services.services.login.service.LoginService;
import dev.henrymolina.legaldoc_web_services.services.model.dto.*;
import dev.henrymolina.legaldoc_web_services.services.model.entity.Order;
import dev.henrymolina.legaldoc_web_services.services.model.entity.OrderByUserDto;
import dev.henrymolina.legaldoc_web_services.services.model.entity.OrderDetails.DetallesOrden;
import dev.henrymolina.legaldoc_web_services.services.model.entity.OrderDetails.DetallesOrdenId;
import dev.henrymolina.legaldoc_web_services.services.model.entity.User;
import dev.henrymolina.legaldoc_web_services.services.model.entity.UserServices;
import dev.henrymolina.legaldoc_web_services.services.model.mapper.OrderMapper;
import dev.henrymolina.legaldoc_web_services.services.model.mapper.UserMapper;
import dev.henrymolina.legaldoc_web_services.services.model.mapper.UserServicesMapper;
import dev.henrymolina.legaldoc_web_services.services.model.repository.*;
import dev.henrymolina.legaldoc_web_services.services.utils.dtos.ServiceStatus;
import dev.henrymolina.legaldoc_web_services.services.utils.dtos.StandardServiceResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Log4j2
public class ComercialServices {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserTypeRepository userTypeRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private DetallesOrdenRepository detallesOrdenRepository;
    @Autowired
    private UserServicesRepository userServicesRepository;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private LoginService loginService;
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    private UserServicesMapper userServicesMapper;
    public ResponseEntity<StandardServiceResponse> getAsesores(Integer pageNum, Integer pageSize) {
        log.info("getAsesores");
        if (pageNum > 0) {
            pageNum = pageNum - 1;
        }
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        try {
            Page<User> page = userRepository.findByTipoUsuarioInAndHabilitadoTrue(userTypeRepository.findById(2L).stream().toList(), pageable);
            Page<UserAsesor> pageDto = page.map(user -> userMapper.userToUserAsesor(user));
            return ResponseEntity.ok(StandardServiceResponse
                    .builder()
                    .data(pageDto)
                    .serviceStatus(
                            ServiceStatus
                                    .builder()
                                    .status(200)
                                    .message("OK")
                                    .build()
                    )
                    .build());
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.ok(StandardServiceResponse
                    .builder()
                    .serviceStatus(
                            ServiceStatus
                                    .builder()
                                    .status(500)
                                    .message("Error interno del servidor")
                                    .build()
                    )
                    .build());
        }
    }

    public ResponseEntity<StandardServiceResponse> getAsesoresById(Integer id) {
        log.info("getAsesoresById");
        try {
            User user = userRepository.findByIdUserAndHabilitadoIsTrue(Long.valueOf(id));
            if (user == null) {
                return ResponseEntity.ok(StandardServiceResponse
                        .builder()
                        .serviceStatus(
                                ServiceStatus
                                        .builder()
                                        .status(404)
                                        .message("No se encontro el usuario")
                                        .build()
                        )
                        .build());
            }
            UserAsesor userAsesor = userMapper.userToUserAsesor(user);
            userAsesor.setServicios(userServicesMapper.toDtoList(userServicesRepository.findByUserId_IdUserAndHabilitadoIsTrue(user.getIdUser())));
            return ResponseEntity.ok(StandardServiceResponse
                    .builder()
                    .data(userAsesor)
                    .serviceStatus(
                            ServiceStatus
                                    .builder()
                                    .status(200)
                                    .message("OK")
                                    .build()
                    )
                    .build());
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.ok(StandardServiceResponse
                    .builder()
                    .serviceStatus(
                            ServiceStatus
                                    .builder()
                                    .status(500)
                                    .message("Error interno del servidor")
                                    .build()
                    )
                    .build());
        }
    }

    public ResponseEntity<StandardServiceResponse> getCarritoDate(Integer pageNum, Integer pageSize, ArrayList itemsId) {
        log.info("getCarritoDate");
        if (pageNum > 0) {
            pageNum = pageNum - 1;
        }
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        try {
            Page<UserServices> page = userServicesRepository.findByIdInAndHabilitadoTrue(itemsId, pageable);
            Page<UserServicesDto> pageDto = page.map(userServices -> userServicesMapper.userServicesToUserServicesDto(userServices));
            return ResponseEntity.ok(StandardServiceResponse
                    .builder()
                    .data(pageDto)
                    .serviceStatus(
                            ServiceStatus
                                    .builder()
                                    .status(200)
                                    .message("OK")
                                    .build()
                    )
                    .build());
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.ok(StandardServiceResponse
                    .builder()
                    .serviceStatus(
                            ServiceStatus
                                    .builder()
                                    .status(500)
                                    .message("Error interno del servidor")
                                    .build()
                    )
                    .build());
        }
    }

    public ResponseEntity<StandardServiceResponse> storeOrder(OrderDto order, HttpServletRequest servlet) {
        UserPublicData user = getUserPublicData(servlet.getHeader("Authorization").trim());
        if (user == null) {
            return ResponseEntity.ok(StandardServiceResponse
                    .builder()
                    .serviceStatus(
                            ServiceStatus
                                    .builder()
                                    .status(500)
                                    .message("Error interno del servidor")
                                    .build()
                    )
                    .build());
        }
        Order orderEntity = orderMapper.orderDtoToOrder(order);
        orderEntity.setUserId(userMapper.userPublicDataToUser(user));
        orderEntity = orderRepository.save(orderEntity);
        if(orderEntity.getId() == null){
            return ResponseEntity.ok(StandardServiceResponse
                    .builder()
                    .serviceStatus(
                            ServiceStatus
                                    .builder()
                                    .status(500)
                                    .message("Error interno del servidor")
                                    .build()
                    )
                    .build());
        }
        List<DetallesOrden> detallesOrdenList = new ArrayList<>();
        for (Long idServicios: order.getIdServicios()){
            DetallesOrden detallesOrden = new DetallesOrden();
            UserServices userServices = userServicesRepository.findByIdAndHabilitadoTrue(idServicios);
            detallesOrden.setIdOrden(orderEntity);
            detallesOrden.setIdServicio(userServices);
            detallesOrden.setEstadoServicio("Pendiente");
            detallesOrden.setPrecioAtual(userServices.getPrecioServicio());
            detallesOrden.setId(DetallesOrdenId.builder().idOrden(orderEntity.getId()).idServicio(userServices.getId()).build());
            detallesOrdenList.add(detallesOrden);
        }
        List response = detallesOrdenRepository.saveAll(detallesOrdenList);
        if(response.size() == 0){
            return ResponseEntity.ok(StandardServiceResponse
                    .builder()
                    .serviceStatus(
                            ServiceStatus
                                    .builder()
                                    .status(500)
                                    .message("Error interno del servidor")
                                    .build()
                    )
                    .build());
        }
        return ResponseEntity.ok(StandardServiceResponse
                .builder()
                        .data(Map.of("id_orden", orderEntity.getId()))
                .serviceStatus(
                        ServiceStatus
                                .builder()
                                .status(200)
                                .message("OK")
                                .build()
                )
                .build());
    }

    public ResponseEntity<StandardServiceResponse> getOrderById(Long id) {
        log.info("getOrderById");
        try {
            Order order = orderRepository.findById(id).get();
            if (order == null) {
                return ResponseEntity.ok(StandardServiceResponse
                        .builder()
                        .serviceStatus(
                                ServiceStatus
                                        .builder()
                                        .status(404)
                                        .message("Orden no encontrada")
                                        .build()
                        )
                        .build());
            }
            OrderAndDetailsDto orderDto = orderMapper.orderToOrderAndDetailsDto(order);
            List<DetallesOrden> detallesOrdenList = detallesOrdenRepository.findById_IdOrden(id);
            orderDto.setDetallesOrden(new ArrayList<>());
            detallesOrdenList.forEach(detallesOrden -> {
                UserServicesAndAsesor userServicesAndAsesor = userServicesMapper.userServicesToUserServicesAndAsesor(detallesOrden.getIdServicio());
                userServicesAndAsesor.setEstado(detallesOrden.getEstadoServicio());
                userServicesAndAsesor.setAsesor(userMapper.userToUserAsesorResumen(detallesOrden.getIdServicio().getUserId()));
                orderDto.getDetallesOrden().add(userServicesAndAsesor);
            });
            return ResponseEntity.ok(StandardServiceResponse
                    .builder()
                    .data(orderDto)
                    .serviceStatus(
                            ServiceStatus
                                    .builder()
                                    .status(200)
                                    .message("OK")
                                    .build()
                    )
                    .build());
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.ok(StandardServiceResponse
                    .builder()
                    .serviceStatus(
                            ServiceStatus
                                    .builder()
                                    .status(500)
                                    .message("Error interno del servidor")
                                    .build()
                    )
                    .build());
        }
    }

    public ResponseEntity<StandardServiceResponse> getOrderByUserId(HttpServletRequest servlet) {
        UserPublicData user = getUserPublicData(servlet.getHeader("Authorization").trim());
        if(user == null){
            return ResponseEntity.ok(StandardServiceResponse
                    .builder()
                    .serviceStatus(
                            ServiceStatus
                                    .builder()
                                    .status(500)
                                    .message("Error interno del servidor")
                                    .build()
                    )
                    .build());
        }
        List<Order> orderList = orderRepository.findOrdersByUserId(user.getIdUser());
        List<OrderByUserDto> orderDtoList= new ArrayList<>();
        orderList.forEach(order -> {
            OrderByUserDto orderDto = orderMapper.orderToOrderByUserDto(order);
            Long cantidadServicios = detallesOrdenRepository.countById_IdOrden(order.getId());
            orderDto.setTotalServicios(cantidadServicios);
            orderDto.setMetodoPago("Paypal");
            orderDtoList.add(orderDto);
        });

        return ResponseEntity.ok(StandardServiceResponse
                .builder()
                .data(orderDtoList)
                .serviceStatus(
                        ServiceStatus
                                .builder()
                                .status(200)
                                .message("OK")
                                .build()
                ).build());
    }


    private UserPublicData getUserPublicData(String authorization) {
        StandardServiceResponse serviceGetUserResponse = (StandardServiceResponse) loginService.getCliente(authorization).getBody();
        if (serviceGetUserResponse.getServiceStatus().getStatus() != 200) {
            return null;
        }
        UserPublicData user = (UserPublicData) serviceGetUserResponse.getData();
        return user;
    }
}
