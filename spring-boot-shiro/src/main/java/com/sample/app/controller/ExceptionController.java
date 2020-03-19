package com.sample.app.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.ShiroException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.sample.app.entity.ResponseWrapper;
import com.sample.app.entity.ReturnCode;

@RestControllerAdvice
public class ExceptionController {

    // 捕捉shiro的异常
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(ShiroException.class)
    public ResponseWrapper handle401(ShiroException e) {
        return ResponseWrapper.markCustom(false, ReturnCode.UNAUTHORIZED.getCode(), 
        		ReturnCode.UNAUTHORIZED.getMsg(), null);
    }

    // 捕捉UnauthorizedException
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseWrapper handle401() {
    	return ResponseWrapper.markCustom(false, ReturnCode.UNAUTHORIZED.getCode(), 
        		ReturnCode.UNAUTHORIZED.getMsg(), null);
    }
    
    // 捕捉UnauthenticatedException
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthenticatedException.class)
    public ResponseWrapper handle401Unauthenticated() {
    	return ResponseWrapper.markCustom(false, ReturnCode.UNAUTHORIZED.getCode(), 
        		ReturnCode.UNAUTHORIZED.getMsg(), null);
    }

    // 捕捉其他所有异常
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseWrapper globalException(HttpServletRequest request, Throwable ex) {
        return ResponseWrapper.markCustom(false, String.valueOf(getStatus(request).value()), 
        		ex.getMessage(), null);
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }
}

