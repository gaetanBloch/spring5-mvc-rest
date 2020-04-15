package guru.springframework.controllers;

import guru.springframework.api.RestError;
import guru.springframework.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * @author Gaetan Bloch
 * Created on 14/04/2020
 */
@RestControllerAdvice
public final class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public RestError handleNotFoundException(Exception exception, WebRequest webRequest) {
        return new RestError(exception, HttpStatus.NOT_FOUND, webRequest);
    }
}
