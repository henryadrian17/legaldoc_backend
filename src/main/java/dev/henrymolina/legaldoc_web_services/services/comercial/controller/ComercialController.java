package dev.henrymolina.legaldoc_web_services.services.comercial.controller;

import dev.henrymolina.legaldoc_web_services.services.comercial.services.ComercialServices;
import dev.henrymolina.legaldoc_web_services.services.model.dto.OrderDto;
import dev.henrymolina.legaldoc_web_services.services.utils.dtos.StandardServiceResponse;
import dev.henrymolina.legaldoc_web_services.services.utils.enums.EndPointProcessApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;

@RestController
@RequestMapping(EndPointProcessApi.ENDPOINT_RAIZ)
@CrossOrigin(origins = "*")
public class ComercialController {
    @Autowired
    private ComercialServices comercialServices;
    @GetMapping(EndPointProcessApi.ENDPOINT_ASESORES)
    public ResponseEntity<StandardServiceResponse> getAsesores(@RequestParam(required = false, defaultValue = "0") Integer pageNum, @RequestParam(required = false, defaultValue = "3") Integer pageSize) {
        return comercialServices.getAsesores(pageNum, pageSize);
    }
    @GetMapping(EndPointProcessApi.ENDPOINT_ASESORES_ID_SERVICES)
    public ResponseEntity<StandardServiceResponse> getAsesoresById(@PathVariable("id") Integer id) {
        return comercialServices.getAsesoresById(id);
    }
    @GetMapping(EndPointProcessApi.CARRITO_DATE)
    public ResponseEntity<StandardServiceResponse> getCarritoDate(@RequestParam(required = false, defaultValue = "0") Integer pageNum, @RequestParam(required = false, defaultValue = "30") Integer pageSize, HttpServletRequest servlet) {
        String carrito_header = servlet.getHeader("Carrito-Compra").trim();
        String[] carrito_header_array = carrito_header.split(",");
        ArrayList<Long> carrito_header_array_int = Arrays.stream(carrito_header_array).map(item ->{
            try{
                return Long.parseLong(item);
            } catch (NumberFormatException e) {
                return 0L;
            }
        }).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        return comercialServices.getCarritoDate(pageNum, pageSize, carrito_header_array_int);
    }

    @PostMapping (EndPointProcessApi.STORE_ORDER)
    public ResponseEntity<StandardServiceResponse> storeOrder(@RequestBody OrderDto order, HttpServletRequest servlet) {
        return comercialServices.storeOrder(order, servlet);
    }

    @GetMapping(EndPointProcessApi.ENDPOINT_ORDER_ID)
    public ResponseEntity<StandardServiceResponse> getOrderById(@PathVariable("id") Long id) {
        return comercialServices.getOrderById(id);
    }

    @GetMapping(EndPointProcessApi.ENDPOINT_ORDER_BY_USER_ID)
    public ResponseEntity<StandardServiceResponse> getOrderByUserId(HttpServletRequest servlet) {
        return comercialServices.getOrderByUserId(servlet);
    }

}
