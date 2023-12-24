/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sim.config;

import sim.data.domain.utils.MessageReturnPojo;
import sim.exceptions.BillingException;
import sim.exceptions.DoesNotExistException;
import java.sql.SQLException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 *
 * @author dit51
 */
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
 
    @ExceptionHandler(value = { Exception.class, SQLException.class })
    protected ResponseEntity<Object> handleResponseEntity(RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "";
        try{
           bodyOfResponse = ex.getCause().getCause().getLocalizedMessage();
        }catch(Exception e){
           bodyOfResponse = ex.getLocalizedMessage();
        }   
        
        return handleExceptionInternal(ex, bodyOfResponse, 
          new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
    
    
    @ExceptionHandler(value = {DoesNotExistException.class, BillingException.class})
    protected ResponseEntity<Object> handleBillingException(BillingException ex, WebRequest request) {
        MessageReturnPojo bodyOfResponse = ex.getResult();
        
        return handleExceptionInternal(ex, bodyOfResponse, 
          new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
}
