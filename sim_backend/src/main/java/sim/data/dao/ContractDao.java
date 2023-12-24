package sim.data.dao;

import org.springframework.stereotype.Repository;
import sim.data.interfaces.IItemDao;
import sim.data.pg.tables.dbo.Contract;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Value;

@Repository("contractDao")
public class ContractDao extends GenericDaoHibernateImpl<Contract, Integer> implements IItemDao<Contract, Integer>{

    public ContractDao(@Value("sim.data.pg.tables.dbo.Contract") Class type) {
        super(type);
    }
    
    
    @Override
    public List<Contract> findAll(){
        return list("Contract.findAll");
    }

    @Override
    public void deleteItem(Integer id) throws Exception {
         Optional.of(read(id)).ifPresent(this::delete);
    }
}
