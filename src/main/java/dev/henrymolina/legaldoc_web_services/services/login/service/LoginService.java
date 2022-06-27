package dev.henrymolina.legaldoc_web_services.services.login.service;

import dev.henrymolina.legaldoc_web_services.services.login.model.dto.UserDto;
import dev.henrymolina.legaldoc_web_services.services.login.model.dto.UserLogin;
import dev.henrymolina.legaldoc_web_services.services.login.model.dto.UserPublicData;
import dev.henrymolina.legaldoc_web_services.services.login.model.entity.User;
import dev.henrymolina.legaldoc_web_services.services.login.model.entity.UserType;
import dev.henrymolina.legaldoc_web_services.services.login.model.mapper.PermisionsMapper;
import dev.henrymolina.legaldoc_web_services.services.login.model.mapper.UserMapper;
import dev.henrymolina.legaldoc_web_services.services.login.model.repository.PermisionsRepository;
import dev.henrymolina.legaldoc_web_services.services.login.model.repository.UserRepository;
import dev.henrymolina.legaldoc_web_services.services.utils.dtos.ServiceStatus;
import dev.henrymolina.legaldoc_web_services.services.utils.dtos.StandardServiceResponse;
import dev.henrymolina.legaldoc_web_services.services.utils.encrypt.PasswordUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Service
@Log4j2
public class LoginService {
    @Value("${string.signup.success}")
    private String signupSuccess;
    @Value("${error.409.email.message}")
    private String error409EmailMessage;
    @Value("${error.409.password.message}")
    private String error409PasswordMessage;
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PermisionsMapper permisionsMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PermisionsRepository permisionsRepository;

    public ResponseEntity<?> create(@Valid UserDto cliente) {
        log.info("Creating user");
        if (!cliente.getContrasena().equals(cliente.getContrasenaConfirmacion())) {
            log.error("Password and password confirmation do not match");
            return new ResponseEntity<>(StandardServiceResponse.builder()
                    .serviceStatus(ServiceStatus
                            .builder()
                            .status(HttpStatus.CONFLICT.value())
                            .message(error409PasswordMessage)
                            .build())
                    .build(), HttpStatus.CONFLICT);

        } else if (userRepository.findByCorreoAndHabilitadoIsTrue(cliente.getCorreo()).size() > 0) {
            log.error("Email already exists");
            return new ResponseEntity<>(StandardServiceResponse.builder()
                    .serviceStatus(ServiceStatus
                            .builder()
                            .status(HttpStatus.CONFLICT.value())
                            .message(error409EmailMessage)
                            .build())
                    .build(), HttpStatus.CONFLICT);
        }
        User user = userMapper.userDtoToUser(cliente);
        user.setHabilitado(true);
        user.setUserType(UserType.builder().id(1L).build());
        user.setFechaCreacion(Timestamp.from(Instant.now()));
        try {
            log.info("Encrypting password");
            user.setSalt(PasswordUtils.getSalt(30));
            user.setContrasena(PasswordUtils.generateSecurePassword(cliente.getContrasena(), user.getSalt()));
        } catch (Exception e) {
            log.error("Error encrypting password");
            user.setContrasena(cliente.getContrasena());
            log.error(e.getMessage());
        }
        if (userRepository.save(user).getId() != null) {
            log.info("User created");
            return new ResponseEntity<>(StandardServiceResponse.builder()
                    .serviceStatus(ServiceStatus
                            .builder()
                            .status(HttpStatus.CREATED.value())
                            .message(signupSuccess)
                            .build())
                    .build(), HttpStatus.CREATED);
        } else {
            log.error("Error creating user");
            return new ResponseEntity<>(StandardServiceResponse.builder()
                    .serviceStatus(ServiceStatus
                            .builder()
                            .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                            .message("Error al crear el usuario")
                            .build())
                    .build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> login(@Valid UserLogin cliente) {
        log.info("Logging in user");
        List<User> users = userRepository.findByCorreoAndHabilitadoIsTrue(cliente.getCorreo());
        if (users.isEmpty()){
            log.error("UNAUTHORIZED");
            return new ResponseEntity<>(StandardServiceResponse.builder()
                    .serviceStatus(ServiceStatus
                            .builder()
                            .status(HttpStatus.UNAUTHORIZED.value())
                            .message("Usuario o contraseña incorrectos")
                            .build())
                    .build(), HttpStatus.UNAUTHORIZED);
        }else {
            User user = users.get(0);
            if (PasswordUtils.verifyUserPassword(cliente.getContrasena(), user.getContrasena(), user.getSalt())) {
                log.info("User logged in");
                UserPublicData userPublicData = userMapper.userToUserPublicData(user);
                userPublicData.setPermisos(permisionsMapper.toDtoList(permisionsRepository.findByUserPermisions(user.getCorreo())));
                return new ResponseEntity<>(StandardServiceResponse.builder()
                        .data(userPublicData)
                        .serviceStatus(ServiceStatus
                                .builder()
                                .status(HttpStatus.OK.value())
                                .message("Usuario logueado")
                                .build())
                        .build(), HttpStatus.OK);
            } else {
                log.error("UNAUTHORIZED");
                return new ResponseEntity<>(StandardServiceResponse.builder()
                        .serviceStatus(ServiceStatus
                                .builder()
                                .status(HttpStatus.UNAUTHORIZED.value())
                                .message("Usuario o contraseña incorrectos")
                                .build())
                        .build(), HttpStatus.UNAUTHORIZED);
            }
        }
    }
}
