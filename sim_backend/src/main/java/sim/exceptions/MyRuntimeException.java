/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sim.exceptions;

/**
 *
 * @author jayson
 */
public class MyRuntimeException extends RuntimeException{

    
    public MyRuntimeException() {
    }

    public MyRuntimeException(String message) {
        super(message);
    }
    
    
}
