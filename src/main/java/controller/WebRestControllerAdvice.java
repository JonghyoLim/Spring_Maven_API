/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import model.ResponseMessage;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 *
 * @author hyoku
 */
@RestControllerAdvice
public class WebRestControllerAdvice {
    
    @ExceptionHandler(CustomNotFoundException.class)
    public ResponseMessage handleNotFoundException(CustomNotFoundException ex) {
        ResponseMessage responseMsg = new ResponseMessage(ex.getMessage());
        
        return responseMsg;
    }
    
    
    
}
