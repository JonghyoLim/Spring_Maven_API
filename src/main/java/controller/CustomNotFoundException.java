/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

/**
 *
 * @author hyoku
 */
public class CustomNotFoundException extends RuntimeException {
    
    public CustomNotFoundException(String msg) {
        super(msg);
    }
    
}
