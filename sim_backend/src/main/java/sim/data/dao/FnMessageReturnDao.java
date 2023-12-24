/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sim.data.dao;

import sim.data.domain.utils.MessageReturnPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

/**
 *
 * @author dit51
 */

@Repository("fnMessageReturnDao")
public class FnMessageReturnDao extends GenericDaoFnHibernateImpl<MessageReturnPojo>
{
    public FnMessageReturnDao() {
    }
    
    @Autowired
    public FnMessageReturnDao(@Value("sim.data.domain.utils.MessageReturnPojo") Class type)
    {
        super(type);
    }
    
}
