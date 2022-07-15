package dev.henrymolina.legaldoc_web_services.services.login.controller;

import dev.henrymolina.legaldoc_web_services.services.login.service.LoginService;
import dev.henrymolina.legaldoc_web_services.services.model.dto.UserDto;
import dev.henrymolina.legaldoc_web_services.services.model.dto.UserLogin;
import dev.henrymolina.legaldoc_web_services.services.model.dto.UserProfile;
import dev.henrymolina.legaldoc_web_services.services.model.dto.UserUpdatePassword;
import dev.henrymolina.legaldoc_web_services.services.utils.enums.EndPointProcessApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping(EndPointProcessApi.ENDPOINT_RAIZ)
@CrossOrigin(origins = "*")
public class LoginController {
    @Autowired
    private LoginService loginService;

    @PostMapping(EndPointProcessApi.ENDPOINT_REGISTER)
    public ResponseEntity<?> create( @Valid @RequestBody UserDto cliente) {
            return loginService.create(cliente);
    }

    @PostMapping(EndPointProcessApi.ENDPOINT_LOGIN)
    public ResponseEntity<?> login(@Valid @RequestBody UserLogin cliente) {
        return loginService.login(cliente);
    }
    @GetMapping(EndPointProcessApi.ENDPOINT_LOGOUT)
    public ResponseEntity<?> logout(HttpServletRequest request) {
        return loginService.logout(request.getHeader("Authorization").trim());
    }
    @GetMapping(EndPointProcessApi.VERIFY_TOKEN)
    public ResponseEntity<?> verifyToken(HttpServletRequest request) {
        return loginService.verifyToken(request.getHeader("Authorization").trim());
    }

    @GetMapping(EndPointProcessApi.ENDPOINT_CLIENTE)
    public ResponseEntity<?> getCliente(HttpServletRequest request) {
        return loginService.getCliente(request.getHeader("Authorization").trim());
    }

    @GetMapping(EndPointProcessApi.ENDPOINT_CLIENT_BY_AUTHORIZATION)
    public ResponseEntity<?> getClienteByAuthorization(HttpServletRequest request) {
        return loginService.getClienteByAuthorization(request.getHeader("Authorization").trim());
    }

    @PutMapping(EndPointProcessApi.ENDPOINT_CLIENT_BY_AUTHORIZATION)
    public ResponseEntity<?> updateCliente(HttpServletRequest request, @RequestBody UserProfile cliente) {
        return loginService.updateCliente(request.getHeader("Authorization").trim(), cliente);
    }

    @PutMapping(EndPointProcessApi.ENDPOINT_CLIENTE_BY_AUTHORIZATION_PASSWORD)
    public ResponseEntity<?> updateClientePassword(HttpServletRequest request, @RequestBody UserUpdatePassword cliente) {
        return loginService.updateClientePassword(request.getHeader("Authorization").trim(), cliente);
    }


}
