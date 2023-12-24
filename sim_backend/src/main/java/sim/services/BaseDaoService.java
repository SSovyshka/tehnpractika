/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sim.services;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


/**
 *
 * @author dit51
 */
public class BaseDaoService {
    
    @PersistenceContext(name = "SIM")
    protected EntityManager em;
    
}
