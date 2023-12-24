package sim.data.dao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import sim.data.interfaces.IItemDao;
import sim.data.pg.tables.dbo.Simcard;

import java.util.List;

@Repository("simcardRankDao")
public class SimcardDao extends GenericDaoHibernateImpl<Simcard, Integer> implements IItemDao<Simcard, Integer> {

    public SimcardDao(@Value("sim.data.pg.tables.dbo.Simcard") Class type) {
        super(type);
    }

    @Override
    public List<Simcard> findAll(){
        return list("Simcard.findAll");
    }

    @Override
    public void deleteItem(Integer id) {
        Simcard item = read(id);
        delete(item);
    }


}
