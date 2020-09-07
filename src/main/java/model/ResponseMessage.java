/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author hyoku
 */
public class ResponseMessage {
    
    private String message;
 
    public ResponseMessage(String msg){
      this.message = msg;
    }

    public String getMessage() {
      return message;
    }

    public void setMessage(String message) {
      this.message = message;
    }
    
}
