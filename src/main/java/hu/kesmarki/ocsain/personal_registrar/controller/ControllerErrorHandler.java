package hu.kesmarki.ocsain.personal_registrar.controller;

import hu.kesmarki.ocsain.personal_registrar.service.exception.PersonNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Slf4j
public class ControllerErrorHandler {
    @ExceptionHandler(PersonNotFoundException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public String handlePersonNotFoundException(PersonNotFoundException personNotFoundException){
        log.warn(personNotFoundException.getMessage(), personNotFoundException);
        return personNotFoundException.getMessage();
    }

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleOtherException(Throwable e){
        log.error(e.getMessage(), e);
        return e.getMessage();
    }
}
