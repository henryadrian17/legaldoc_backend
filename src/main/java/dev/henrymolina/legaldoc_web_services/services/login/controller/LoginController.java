package dev.henrymolina.legaldoc_web_services.services.login.controller;

import dev.henrymolina.legaldoc_web_services.services.login.model.dto.UserDto;
import dev.henrymolina.legaldoc_web_services.services.login.model.dto.UserLogin;
import dev.henrymolina.legaldoc_web_services.services.login.service.LoginService;
import dev.henrymolina.legaldoc_web_services.services.utils.enums.EndPointProcessApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(EndPointProcessApi.ENDPOINT_RAIZ)
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



}
