package com.authon.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.authon.oauth.server.OAuth2Exception;


@ControllerAdvice
public class OAuthExceptionHandlerController extends ResponseEntityExceptionHandler{

    @ExceptionHandler({OAuth2Exception.class})
    public ResponseEntity<Object> handleNotFoundException(Exception exception, WebRequest request){
        return new ResponseEntity<Object>(exception.getMessage(), new HttpHeaders(), HttpStatus.UNAUTHORIZED);
    }

}
