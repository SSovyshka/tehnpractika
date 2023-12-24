/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sim.data.interfaces;

import java.io.Serializable;
/**
 *
 * @author dit51
 */
public interface IItemDao<T, PK extends Serializable> extends IGenericDao<T,PK>
{
    void deleteItem(PK id) throws Exception;
}
