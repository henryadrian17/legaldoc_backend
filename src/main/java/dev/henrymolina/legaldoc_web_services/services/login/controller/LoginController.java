package dev.henrymolina.legaldoc_web_services.services.login.controller;

import dev.henrymolina.legaldoc_web_services.services.login.model.dto.UserDto;
import dev.henrymolina.legaldoc_web_services.services.login.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

@RestController
@RequestMapping("/legaldoc/api/v1")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/signin")
    public ResponseEntity<?> create( @Valid @RequestBody UserDto cliente) {
            return loginService.create(cliente);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserDto cliente, BindingResult result) {
        return loginService.login(cliente);
    }

}
