package ua.training.hospital.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ua.training.hospital.controller.dto.ApiError;
import ua.training.hospital.controller.dto.CreationResponse;

import java.util.ResourceBundle;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice

public class ExceptionHandler  extends ResponseEntityExceptionHandler {
    @Autowired
    private MessageSource messageSource;



    @Override

    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String message = messageSource.getMessage("exceptionHandler.json.message",null,request.getLocale());
        String error = ex.toString();
        return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST,message,error));
    }

    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}
