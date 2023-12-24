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
public class LoginExistException extends RuntimeException{

    
    public LoginExistException() {
    }

    public LoginExistException(String message) {
        super(message);
    }
    
    
}
