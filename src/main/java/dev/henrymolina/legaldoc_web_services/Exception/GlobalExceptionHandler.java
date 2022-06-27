package dev.henrymolina.legaldoc_web_services.Exception;

import dev.henrymolina.legaldoc_web_services.services.utils.dtos.ServiceStatus;
import dev.henrymolina.legaldoc_web_services.services.utils.dtos.StandardServiceResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;

@ControllerAdvice
@RestController
@Log4j2
public class GlobalExceptionHandler {
    @Value("${error.422.message}")
    private String error422Message;

    @Value("${error.500.message}")
    private String error500Message;
    /**
     * Constraint violation exception handler
     *
     * @param ex ConstraintViolationException
     * @return List<ValidationError> - list of ValidationError built
     * from set of ConstraintViolation
     */
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(ConstraintViolationException.class)
    public StandardServiceResponse
    handleConstraintViolation(ConstraintViolationException ex) {
        return StandardServiceResponse.builder()
                .serviceStatus(ServiceStatus.builder()
                        .status(HttpStatus.UNPROCESSABLE_ENTITY.value())
                        .message(error422Message)
                        .build())
                .data(buildValidationErrors(ex.getConstraintViolations()))
                .build();
    }

    /**
     * Build list of ValidationError from set of ConstraintViolation
     *
     * @param violations Set<ConstraintViolation<?>> - Set
     *                   of parameterized ConstraintViolations
     * @return List<ValidationError> - list of validation errors
     */
    private List<ValidationError> buildValidationErrors(
            Set<ConstraintViolation<?>> violations) {
        return violations.
                stream().
                map(violation ->
                        ValidationError.builder().
                                field(
                                        StreamSupport.stream(
                                                        violation.getPropertyPath().spliterator(), false).
                                                reduce((first, second) -> second).
                                                orElse(null).
                                                toString()
                                ).
                                error(violation.getMessage()).
                                build()).
                collect(toList());
    }

    /**
     * Method Argument Not Valid Exception handler
     *
     * @param ex MethodArgumentNotValidException
     * @return List<ValidationError> - list of ValidationError built
     * from set of ConstraintViolation
     */
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public StandardServiceResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        return StandardServiceResponse.builder()
                .serviceStatus(ServiceStatus.builder()
                        .status(HttpStatus.UNPROCESSABLE_ENTITY.value())
                        .message(error422Message)
                        .build())
                .data(buildValidationErrors(ex.getBindingResult().getFieldErrors()))
                .build();
    }

    /**
     * Build list of ValidationError from List<FieldError> fieldErrors
     *
     * @param fieldErrors List<FieldError> fieldErrors - List
     * @return List<ValidationError> - list of validation errors
     */
    private List<ValidationError> buildValidationErrors(
            List<FieldError> fieldErrors) {
        return fieldErrors.
                stream().
                map(fieldError ->
                        ValidationError.builder().
                                field(
                                        fieldError.getField()
                                ).
                                error(fieldError.getDefaultMessage()).
                                build()).
                collect(toList());
    }

    /**
     * Method Argument Not Valid Exception handler
     *
     * @param ex MethodArgumentNotValidException
     * @return List<ValidationError> - list of ValidationError built
     * from set of ConstraintViolation
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public StandardServiceResponse handleSQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException ex) {
        return StandardServiceResponse.builder()
                .serviceStatus(ServiceStatus.builder()
                        .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .message(error500Message)
                        .build())
                .data(buildValidationErrors(ex.getMessage()))
                .build();
    }
    /**
     * Build list of ValidationError from String message
     *
     * @param message String message - String
     * @return List<ValidationError> - list of validation errors
     */

    private List<ValidationError> buildValidationErrors(String message) {
        if (message.contains("Duplicate entry")) {
            return Arrays.asList(ValidationError.builder()
                    .field(message.split("Duplicate entry")[1].split("for key")[0].trim())
                    .error("Duplicate entry")
                    .build());
        } else {
            return null;
        }
    }


}
