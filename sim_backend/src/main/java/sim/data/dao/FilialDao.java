package sim.data.dao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import sim.data.pg.tables.dictionary.Filial;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import sim.data.interfaces.IItemDao;

@Repository("filialDao")
public class FilialDao extends GenericDaoHibernateImpl<Filial, Integer> implements IItemDao<Filial, Integer> {

    public FilialDao(@Value("sim.data.pg.tables.dictionary.Filial") Class type) {
        super(type);
    }

    @Override
    public List<Filial> findAll(){
        return list("Filial.findAll");
    }
    
    @Override
    public void deleteItem(Integer id) throws SQLException {
        Optional.of(read(id)).ifPresent(this::delete);
    }

}
