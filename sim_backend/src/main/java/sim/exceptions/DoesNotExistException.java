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
public class DoesNotExistException extends BillingException{

    
    public DoesNotExistException() {
    }

    public DoesNotExistException(Integer codeError, String message) {
        super(codeError, message);
    }
    
    
}
