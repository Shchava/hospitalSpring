package ua.training.hospital.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ua.training.hospital.controller.dto.ApiError;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class HospitalExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Logger logger = LogManager.getLogger(HospitalExceptionHandler.class);

    @Autowired
    private MessageSource messageSource;

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String message = messageSource.getMessage("exceptionHandler.json.message",null,request.getLocale());
        String error = ex.toString();
        logger.warn("trying to pass unparseable JSON: " + ex.toString());
        return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST,message,error));
    }

//    @ExceptionHandler(value = { MethodArgumentTypeMismatchException.class}) todo: uncomment
    protected ModelAndView handleTypeMismatchException(
            MethodArgumentTypeMismatchException ex, WebRequest request) {
        ModelAndView returning = new ModelAndView("/error");
        logger.debug("encountered MethodArgumentTypeMismatchException : " + ex.toString());
        if(ex.getName().equals("idPatient")||
                ex.getName().equals("idDiagnosis")
                ||ex.getName().equals("pageNumber")){
            logger.debug("assuming that it's wrong path variable and return 404");
            returning.setStatus(HttpStatus.BAD_REQUEST);
        }else{
            logger.debug("cant recognize exception, returning 400");
            returning.setStatus(HttpStatus.BAD_REQUEST);
        }
        return returning;
    }

    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}
