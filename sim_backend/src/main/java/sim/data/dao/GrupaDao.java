package sim.data.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import sim.data.pg.tables.dictionary.Grupa;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.List;
import sim.data.interfaces.IItemDao;

@Repository("grupaDao")
public class GrupaDao extends GenericDaoHibernateImpl<Grupa, BigInteger> implements IItemDao<Grupa, BigInteger> {

    @Autowired
    public GrupaDao(@Value("sim.data.pg.tables.dictionary.Grupa") Class type) {
        super(type);
    }

    @Override
    public List<Grupa> findAll(){
        return list("Grupa.findAll");
    }

    @Override
    public void deleteItem(BigInteger id) throws SQLException {
        Grupa item = (Grupa) read(id);
        delete(item);
    }


}
