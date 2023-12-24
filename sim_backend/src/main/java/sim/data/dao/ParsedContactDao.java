package sim.data.dao;

import org.springframework.stereotype.Repository;
import sim.data.interfaces.IItemDao;
import sim.data.pg.tables.ParsedContract;

@Repository("parsedContractDao")
public class ParsedContactDao extends GenericDaoHibernateImpl<ParsedContract, String> implements IItemDao<ParsedContract, String> {
    @Override
    public void deleteItem(String id) throws Exception {

    }
}
