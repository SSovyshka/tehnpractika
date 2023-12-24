/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sim.config;

import sim.exceptions.BillingException;

/**
 *
 * @author dit51
 */
public interface IRestRequest<T,P> {
    
    public T getEntity(String url) throws BillingException, Exception;
    
    public T postEntity(String url, P payloadModel) throws BillingException, Exception;
    
    public T putEntity(String url, P payloadModel) throws BillingException, Exception;
    
    public T deleteEntity(String url) throws BillingException, Exception;
    
}
