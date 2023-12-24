/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sim.exceptions;

/**
 *
 * @author dit51
 */
public class LoginNotFoundException extends RuntimeException{

    
    public LoginNotFoundException() {
    }

    public LoginNotFoundException(String message) {
        super(message);
    }
    
    
}
