package dev.henrymolina.legaldoc_web_services.services.login.service;

import dev.henrymolina.legaldoc_web_services.services.login.model.dto.UserDto;
import dev.henrymolina.legaldoc_web_services.services.login.model.mapper.UserMapper;
import dev.henrymolina.legaldoc_web_services.services.login.model.repository.UserRepository;
import dev.henrymolina.legaldoc_web_services.services.utils.dtos.ServiceStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

@Service
public class LoginService {
    @Value("${string.signup.success}")
    private String signupSuccess;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRepository userRepository;
    public ResponseEntity<?> create(@Valid UserDto cliente) {
        try {
            userRepository.save(userMapper.userDtoToUser(cliente));
            return new ResponseEntity<>(ServiceStatus.builder().status(HttpStatus.CREATED.value()).message(signupSuccess).build(),HttpStatus.CREATED);
        } catch (ConstraintViolationException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> login(@Valid UserDto cliente) {
        return null;
    }
}
