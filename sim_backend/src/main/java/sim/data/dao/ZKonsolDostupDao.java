/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sim.data.dao;

import sim.data.interfaces.IZKonsolDostupDao;
import sim.data.pg.tables.ZKonsolDostup;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

/**
 *
 * @author dit51
 */
@Repository("zkonsolDostupDao")
public class ZKonsolDostupDao extends GenericDaoHibernateImpl<ZKonsolDostup, BigInteger> implements IZKonsolDostupDao<ZKonsolDostup, BigInteger>{

    public ZKonsolDostupDao() {
    }

    @Autowired
    public ZKonsolDostupDao(@Value("sim.data.pg.tables.ZKonsolDostup") Class type) {
        super(type);
    }
    
    @Override
    public List<ZKonsolDostup> findAll()
    {
        return list("ZKonsolDostup.findAll");
    }

    @Override
    public void deleteItem(BigInteger id) throws SQLException {
         ZKonsolDostup item = (ZKonsolDostup) read(id);
         delete(item);
    }
}
