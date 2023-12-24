package sim.data.dao;

import org.springframework.stereotype.Repository;
import sim.data.interfaces.IItemDao;
import sim.data.pg.tables.ParsedContract;
import sim.data.pg.tables.dictionary.Grupa;

import java.math.BigInteger;


@Repository("testDao")
public class TestDao  extends GenericDaoHibernateImpl<ParsedContract, String> implements IItemDao<ParsedContract, String> {
    @Override
    public void deleteItem(String id) throws Exception {

    }
}
