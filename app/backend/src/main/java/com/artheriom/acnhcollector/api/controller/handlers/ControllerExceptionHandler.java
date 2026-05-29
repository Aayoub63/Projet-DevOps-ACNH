package com.artheriom.acnhcollector.api.controller.handlers;


import com.artheriom.acnhcollector.api.domain.dto.APIErrorDTO;
import com.artheriom.acnhcollector.api.exceptions.ForbiddenException;
import com.artheriom.acnhcollector.api.exceptions.NotFoundException;
import com.artheriom.acnhcollector.api.exceptions.UnauthorizedException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<Object> handleIllegalArgumentException(Exception ex, WebRequest request) {
        APIErrorDTO error = new APIErrorDTO(HttpStatus.BAD_REQUEST.value(), StringUtils.isNotBlank(ex.getMessage()) ? ex.getMessage() : "Bad request : some values are invalid");

        return new ResponseEntity<>(error, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    //Exception.class,
    @ExceptionHandler({NullPointerException.class})
    public ResponseEntity<Object> exception(Exception ex, WebRequest request) {
        APIErrorDTO error = new APIErrorDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), StringUtils.isNotBlank(ex.getMessage()) ? ex.getMessage() : "Something went wrong.");

        return new ResponseEntity<>(error, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<Object> handleNotFoundException(Exception ex, WebRequest request){
        APIErrorDTO error = new APIErrorDTO(HttpStatus.NOT_FOUND.value(), StringUtils.isNotBlank(ex.getMessage())? ex.getMessage() : "Not found.");

        return new ResponseEntity<>(error, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ForbiddenException.class})
    public ResponseEntity<Object> handleForbiddenException(Exception ex, WebRequest request){
        APIErrorDTO error = new APIErrorDTO(HttpStatus.FORBIDDEN.value(), StringUtils.isNotBlank(ex.getMessage()) ? ex.getMessage() : "Forbidden");

        return new ResponseEntity<>(error, new HttpHeaders(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({UnauthorizedException.class})
    public ResponseEntity<Object> handleUnauthorizedException(Exception ex, WebRequest request){
        APIErrorDTO error = new APIErrorDTO(HttpStatus.UNAUTHORIZED.value(), StringUtils.isNotBlank(ex.getMessage()) ? ex.getMessage() : "Unauthorized");

        return new ResponseEntity<>(error, new HttpHeaders(), HttpStatus.UNAUTHORIZED);

    }
}