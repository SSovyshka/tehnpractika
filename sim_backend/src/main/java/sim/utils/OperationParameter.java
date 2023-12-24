/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sim.utils;

/**
 *
 * @author dit51
 */
public class OperationParameter<T> {
    
    private String operation;
    private T paramValue;

    public OperationParameter() {
    }

    public OperationParameter(String operation, T paramValue) {
        this.operation = operation;
        this.paramValue = paramValue;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public T getParamValue() {
        return paramValue;
    }

    public void setParamValue(T paramValue) {
        this.paramValue = paramValue;
    }
    
    
    
    
}
