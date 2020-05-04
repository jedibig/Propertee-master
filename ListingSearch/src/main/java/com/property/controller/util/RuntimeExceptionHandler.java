package com.property.controller.util;

import com.property.exception.InvalidModelInputException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class RuntimeExceptionHandler {
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(InvalidModelInputException.class)
    public String daoExceptionHandler(Model m){
        m.addAttribute("error", "Ivalid input");
        return "index";
    }
}
