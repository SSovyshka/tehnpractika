/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sim.exceptions;

import sim.data.domain.utils.MessageReturnPojo;




/**
 *
 * @author dit51
 */
public class BillingException extends RuntimeException {
 
    private Integer codeError;

    public BillingException() {
    }

    public BillingException(Integer codeError, String message) {
        super(message);
        this.codeError = codeError;
    }

    public MessageReturnPojo getResult() {
        return new MessageReturnPojo(this.codeError, this.getMessage());
    }

}
