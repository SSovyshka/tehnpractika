package sim.data.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import sim.data.pg.tables.dictionary.OfficialRank;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.List;
import sim.data.interfaces.IItemDao;

@Repository("officialRankDao")
public class OfficialRankDao extends GenericDaoHibernateImpl<OfficialRank, Integer> implements IItemDao<OfficialRank, Integer> {

    @Autowired
    public OfficialRankDao(@Value("sim.data.pg.tables.dictionary.OfficialRank") Class type) {
        super(type);
    }

    @Override
    public List<OfficialRank> findAll(){
        return list("OfficialRank.findAll");
    }

    @Override
    public void deleteItem(Integer id) throws Exception {
        OfficialRank item = read(id);
        delete(item);
    }
}
