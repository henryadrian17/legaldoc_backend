package dev.henrymolina.legaldoc_web_services.services.login.service;

import dev.henrymolina.legaldoc_web_services.services.model.dto.*;
import dev.henrymolina.legaldoc_web_services.services.model.entity.User;
import dev.henrymolina.legaldoc_web_services.services.model.entity.UserType;
import dev.henrymolina.legaldoc_web_services.services.model.mapper.PermisionsMapper;
import dev.henrymolina.legaldoc_web_services.services.model.mapper.UserMapper;
import dev.henrymolina.legaldoc_web_services.services.model.repository.PermisionsRepository;
import dev.henrymolina.legaldoc_web_services.services.model.repository.UserRepository;
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
        user.setTipoUsuario(UserType.builder().id(1L).build());
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
        //generate unique tocken acces and expiration date
        try{
            log.info("Generating token");
            user.setTockenDeAcceso(PasswordUtils.generateSecurePassword(user.getCorreo() + Instant.now() + user.getNombre() + user.getFechaCreacion(), user.getSalt()));
            user.setFechaExpiracionTocken(Timestamp.from(Instant.now().plusSeconds(60 * 60 * 24)));
        }catch (Exception e){
            log.error("Error generating token");
            log.error(e.getMessage());
        }
        user = userRepository.save(user);
        if (userRepository.save(user).getIdUser()!= null) {
            log.info("User created");
            return new ResponseEntity<>(StandardServiceResponse.builder()
                    .serviceStatus(ServiceStatus
                            .builder()
                            .status(HttpStatus.CREATED.value())
                            .message(signupSuccess)
                            .build())
                    .data(userMapper.userToUserPublicData(user))
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
                ////generate unique tocken acces and expiration date
                try {
                    log.info("Generating token");
                    user.setTockenDeAcceso(PasswordUtils.generateSecurePassword(user.getCorreo() + Instant.now() + user.getNombre() + user.getFechaCreacion(), user.getSalt()));
                    user.setFechaExpiracionTocken(Timestamp.from(Instant.now().plusSeconds(60 * 60 * 24)));
                    userRepository.save(user);
                }catch (Exception e){
                    log.error("Error generating token");
                    log.error(e.getMessage());
                }
                UserPublicData userPublicData = userMapper.userToUserPublicData(user);
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

    public ResponseEntity<?> logout(String token) {
        log.info("Logging out user");
        List<User> users = userRepository.findByTockenDeAcceso(token);
        if (users.isEmpty()){
            log.error("UNAUTHORIZED");
            return new ResponseEntity<>(StandardServiceResponse.builder()
                    .serviceStatus(ServiceStatus
                            .builder()
                            .status(HttpStatus.UNAUTHORIZED.value())
                            .message("Usuario no logueado")
                            .build())
                    .build(), HttpStatus.UNAUTHORIZED);
        }else {
            User user = users.get(0);
            user.setTockenDeAcceso(null);
            user.setFechaExpiracionTocken(null);
            userRepository.save(user);
            log.info("User logged out");
            return new ResponseEntity<>(StandardServiceResponse.builder()
                    .serviceStatus(ServiceStatus
                            .builder()
                            .status(HttpStatus.OK.value())
                            .message("Usuario deslogueado")
                            .build())
                    .build(), HttpStatus.OK);
        }
    }

    public ResponseEntity<?> verifyToken(String authorization) {
        log.info("Verifying token");
        List<User> users = userRepository.findByTockenDeAcceso(authorization);
        if (users.isEmpty()){
            log.error("UNAUTHORIZED");
            return new ResponseEntity<>(StandardServiceResponse.builder()
                    .serviceStatus(ServiceStatus
                            .builder()
                            .status(HttpStatus.UNAUTHORIZED.value())
                            .message("Usuario no logueado")
                            .build())
                    .build(), HttpStatus.UNAUTHORIZED);
        }else {
            User user = users.get(0);
            if (user.getFechaExpiracionTocken().after(Timestamp.from(Instant.now()))){
                log.info("Token verified");
                return new ResponseEntity<>(StandardServiceResponse.builder()
                        .serviceStatus(ServiceStatus
                                .builder()
                                .status(HttpStatus.OK.value())
                                .message("Token verificado")
                                .build())
                        .build(), HttpStatus.OK);
            }else {
                log.error("UNAUTHORIZED");
                return new ResponseEntity<>(StandardServiceResponse.builder()
                        .serviceStatus(ServiceStatus
                                .builder()
                                .status(HttpStatus.UNAUTHORIZED.value())
                                .message("Token expirado")
                                .build())
                        .build(), HttpStatus.UNAUTHORIZED);
            }
        }
    }

    public ResponseEntity<?> getCliente(String authorization) {
        log.info("Getting cliente");
        List<User> users = userRepository.findByTockenDeAcceso(authorization);
        if (users.isEmpty()){
            log.error("UNAUTHORIZED");
            return new ResponseEntity<>(StandardServiceResponse.builder()
                    .serviceStatus(ServiceStatus
                            .builder()
                            .status(HttpStatus.UNAUTHORIZED.value())
                            .message("Usuario no logueado")
                            .build())
                    .build(), HttpStatus.UNAUTHORIZED);
        }else {
            User user = users.get(0);
            if (user.getFechaExpiracionTocken().after(Timestamp.from(Instant.now()))){
                log.info("Cliente retrieved");
                return new ResponseEntity<>(StandardServiceResponse.builder()
                        .serviceStatus(ServiceStatus
                                .builder()
                                .status(HttpStatus.OK.value())
                                .message("Cliente obtenido")
                                .build())
                        .data(userMapper.userToUserPublicData(user))
                        .build(), HttpStatus.OK);
            }else {
                log.error("UNAUTHORIZED");
                return new ResponseEntity<>(StandardServiceResponse.builder()
                        .serviceStatus(ServiceStatus
                                .builder()
                                .status(HttpStatus.UNAUTHORIZED.value())
                                .message("Token expirado")
                                .build())
                        .build(), HttpStatus.UNAUTHORIZED);
            }
        }
    }

    public ResponseEntity<?> getClienteByAuthorization(String authorization) {
        log.info("Getting cliente");
        List<User> users = userRepository.findByTockenDeAcceso(authorization);
        if (users.isEmpty()){
            log.error("UNAUTHORIZED");
            return new ResponseEntity<>(StandardServiceResponse.builder()
                    .serviceStatus(ServiceStatus
                            .builder()
                            .status(HttpStatus.UNAUTHORIZED.value())
                            .message("Usuario no logueado")
                            .build())
                    .build(), HttpStatus.UNAUTHORIZED);
        }else {
            User user = users.get(0);
            if (user.getFechaExpiracionTocken().after(Timestamp.from(Instant.now()))){
                log.info("Cliente retrieved");
                return new ResponseEntity<>(StandardServiceResponse.builder()
                        .serviceStatus(ServiceStatus
                                .builder()
                                .status(HttpStatus.OK.value())
                                .message("Cliente obtenido")
                                .build())
                        .data(userMapper.userToUserProfile(user))
                        .build(), HttpStatus.OK);
            }else {
                log.error("UNAUTHORIZED");
                return new ResponseEntity<>(StandardServiceResponse.builder()
                        .serviceStatus(ServiceStatus
                                .builder()
                                .status(HttpStatus.UNAUTHORIZED.value())
                                .message("Token expirado")
                                .build())
                        .build(), HttpStatus.UNAUTHORIZED);
            }
        }
    }

    public ResponseEntity<?> updateCliente(String authorization, UserProfile cliente) {
        log.info("Updating cliente");
        List<User> users = userRepository.findByTockenDeAcceso(authorization);
        if (users.isEmpty()){
            log.error("UNAUTHORIZED");
            return new ResponseEntity<>(StandardServiceResponse.builder()
                    .serviceStatus(ServiceStatus
                            .builder()
                            .status(HttpStatus.UNAUTHORIZED.value())
                            .message("Usuario no logueado")
                            .build())
                    .build(), HttpStatus.UNAUTHORIZED);
        }else {
            User user = users.get(0);
            if (user.getFechaExpiracionTocken().after(Timestamp.from(Instant.now()))) {
                User userUpdated = userMapper.userProfileToUser(cliente);
                userUpdated.setIdUser(user.getIdUser());
                userUpdated.setTockenDeAcceso(user.getTockenDeAcceso());
                userUpdated.setFechaExpiracionTocken(user.getFechaExpiracionTocken());
                userUpdated.setFechaCreacion(user.getFechaCreacion());
                userUpdated.setContrasena(user.getContrasena());
                userUpdated.setSalt(user.getSalt());
                userUpdated.setTipoUsuario(user.getTipoUsuario());
                userUpdated.setHabilitado(user.getHabilitado());
                userUpdated = userRepository.save(userUpdated);
                log.info("Cliente updated");
                return new ResponseEntity<>(StandardServiceResponse.builder()
                        .serviceStatus(ServiceStatus
                                .builder()
                                .status(HttpStatus.OK.value())
                                .message("Informacion del cliente ".concat(cliente.getNombreUsuario()).concat(" actualizada"))
                                .build())
                        .data(userMapper.userToUserProfile(userUpdated))
                        .build(), HttpStatus.OK);
            } else {
                log.error("UNAUTHORIZED");
                return new ResponseEntity<>(StandardServiceResponse.builder()
                        .serviceStatus(ServiceStatus
                                .builder()
                                .status(HttpStatus.UNAUTHORIZED.value())
                                .message("Token expirado")
                                .build())
                        .build(), HttpStatus.UNAUTHORIZED);
            }
        }
    }

    public ResponseEntity<?> updateClientePassword(String authorization, UserUpdatePassword cliente) {
        if (!cliente.isValid()){
            log.error("UNAUTHORIZED");
            return new ResponseEntity<>(StandardServiceResponse.builder()
                    .serviceStatus(ServiceStatus
                            .builder()
                            .status(HttpStatus.UNAUTHORIZED.value())
                            .message(cliente.getErrorMessage())
                            .build())
                    .build(), HttpStatus.UNAUTHORIZED);
        }
        log.info("Updating cliente");
        List<User> users = userRepository.findByTockenDeAcceso(authorization);
        if (users.isEmpty()){
            log.error("UNAUTHORIZED");
            return new ResponseEntity<>(StandardServiceResponse.builder()
                    .serviceStatus(ServiceStatus
                            .builder()
                            .status(HttpStatus.UNAUTHORIZED.value())
                            .message("Usuario no logueado")
                            .build())
                    .build(), HttpStatus.UNAUTHORIZED);
        }else {
            User user = users.get(0);
            if (PasswordUtils.verifyUserPassword(cliente.getContrasenaActual(), user.getContrasena(), user.getSalt())){
                user.setContrasena(PasswordUtils.generateSecurePassword(cliente.getContrasena(), user.getSalt()));
                user = userRepository.save(user);
                log.info("Cliente updated");
                return new ResponseEntity<>(StandardServiceResponse.builder()
                        .serviceStatus(ServiceStatus
                                .builder()
                                .status(HttpStatus.OK.value())
                                .message("Contrasena del cliente ".concat(user.getNombre()).concat(" actualizada"))
                                .build())
                        .data(userMapper.userToUserProfile(user))
                        .build(), HttpStatus.OK);
            }
            else {
                log.error("UNAUTHORIZED");
                return new ResponseEntity<>(StandardServiceResponse.builder()
                        .serviceStatus(ServiceStatus
                                .builder()
                                .status(HttpStatus.UNAUTHORIZED.value())
                                .message("Contrasena actual incorrecta")
                                .build())
                        .build(), HttpStatus.UNAUTHORIZED);
            }
        }
    }
}
