/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sendgrid.SendGrid;

/**
 *
 * @author hyoku
 */
@Configuration
public class SendGridConfig {

   private String appKey = "";
   
   @Bean
   public SendGrid getSendGrid(){
       return new SendGrid(appKey);
   }
    
    
}
