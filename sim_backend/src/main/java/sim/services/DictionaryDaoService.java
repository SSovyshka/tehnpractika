package sim.services;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import sim.data.domain.utils.MessageReturnPojo;
import sim.data.pg.tables.dictionary.Filial;
import sim.data.pg.tables.dictionary.Grupa;
import sim.data.pg.tables.dictionary.OfficialRank;
import sim.exceptions.DoesNotExistException;


import java.math.BigInteger;
import java.util.List;

import sim.data.interfaces.IItemDao;
import sim.exceptions.BillingException;


@Repository("dictionaryDaoService")
@Slf4j
@Transactional
public class DictionaryDaoService extends BaseDaoService{

    @Autowired private IItemDao<Filial, Integer> filialDao;
    @Autowired private IItemDao<Grupa, BigInteger> grupaDao;
    @Autowired private IItemDao<OfficialRank, Integer> officialRankDao;




    public MessageReturnPojo test() {
        try {
            return new MessageReturnPojo(0, "Успішно виконано");
        } catch (Exception ex) {
            return new MessageReturnPojo(-1, ex.getMessage());
        }
    }

    public List<Filial> getFilials(){
        return filialDao.findAll();
    }

//    public List<Filial> getFilial(Filial model){
//        DetachedCriteria criteria = DetachedCriteria.forClass(Filial.class);
//
//        if (model.getFilialid() != null){
//            criteria.add(Restrictions.eq("filialid", model.getFilialid()));
//        }
//
//        return filialDao.list(criteria);
//    }

    public List<Filial> filialSearch(Filial model) {
        DetachedCriteria crit = DetachedCriteria.forClass(Filial.class);

        //Id
        if (model.getFilialid() != null) {
            crit.add(Restrictions.eq("filialid", model.getFilialid()));
        }

        //Filial name
        if (model.getFilial() != null && !model.getFilial().equals("")) {
            if (model.getFilial().contains("*")) {
                crit.add(Restrictions.like("filial", model.getFilial().replaceAll("\\*", "\\%")).ignoreCase());
            } else {
                crit.add(Restrictions.like("filial", "%" + model.getFilial() + "%").ignoreCase());
            }
        }
        return filialDao.list(crit);
    }

    public Filial createFilial(Filial model){
        if (!filialSearch(model).isEmpty())
            throw new BillingException();

        return filialDao.create(model);
    }

    public MessageReturnPojo updateFilial(Filial model) {
        try {
            filialDao.update(model);
            return new MessageReturnPojo(0, "Update was successful!");
        } catch (Exception e) {
            return new MessageReturnPojo(-1, e.getMessage());
        }
    }

    public MessageReturnPojo deleteFilial(Integer id){
        try {
            filialDao.deleteItem(id);
            return new MessageReturnPojo(0, "Item was deleted successful!");
        } catch (NullPointerException npe) {
            throw new BillingException(-1, "Error: Item does not exist");
        } catch (Exception e) {
            throw new BillingException(-500, e.getMessage());
        }
    }

    public List<Grupa> getGrups(){
        return grupaDao.findAll();
    }
    public List<Grupa> groupSearch(Grupa model){
        
        DetachedCriteria crit = DetachedCriteria.forClass(Grupa.class);

        //Id
        if (model.getGrupaid() != null) {
            crit.add(Restrictions.eq("grupaid", model.getGrupaid()));
        }
        
        //Group name
        if (model.getGrupa() != null && !model.getGrupa().equals("")) {
            if (model.getGrupa().contains("*")) {
                crit.add(Restrictions.like("grupa", model.getGrupa().replaceAll("\\*", "\\%")).ignoreCase());
            } else {
                crit.add(Restrictions.like("grupa", "%" + model.getGrupa() + "%").ignoreCase());
            }
        }
        return grupaDao.list(crit);
    }

    public Grupa createGrupa(Grupa model){
        if (!groupSearch(model).isEmpty())
            throw new BillingException();

        return grupaDao.create(model);
    }

    public MessageReturnPojo updateGrupa(Grupa model) {
        try {
            grupaDao.update(model);
            return new MessageReturnPojo(0, "Update was successful!");
        } catch (Exception e) {
            return new MessageReturnPojo(-1, e.getMessage());
        }
    }

    public MessageReturnPojo deleteGrupa(BigInteger id){
        try {
            grupaDao.deleteItem(id);
            return new MessageReturnPojo(0, "Delete was successful!");
        } catch (Exception e) {
            return new MessageReturnPojo(-1, e.getMessage());
        }
    }

    public List<OfficialRank> getOfficialRanks() {
        return officialRankDao.findAll();
    }

    public List<OfficialRank> officialRankSearch(OfficialRank model){

        DetachedCriteria crit = DetachedCriteria.forClass(OfficialRank.class);

        //Id
        if (model.getOfficialrankid() != null) {
            crit.add(Restrictions.eq("officialrankid", model.getOfficialrankid()));
        }

        //Group name
        if (model.getName() != null && !model.getName().equals("")) {
            if (model.getName().contains("*")) {
                crit.add(Restrictions.like("name", model.getName().replaceAll("\\*", "\\%")).ignoreCase());
            } else {
                crit.add(Restrictions.like("name", "%" + model.getName() + "%").ignoreCase());
            }
        }
        return officialRankDao.list(crit);
    }

    public OfficialRank createOfficialRank(OfficialRank model){
        if (!officialRankSearch(model).isEmpty())
            throw new BillingException(-500,"Item is already exist");

        return officialRankDao.create(model);
    }

    public MessageReturnPojo updateOfficialRank(OfficialRank model) {
        try {
            officialRankDao.update(model);
            return new MessageReturnPojo(0, "Update was successful!");
        } catch (Exception e) {
            return new MessageReturnPojo(-1, e.getMessage());
        }
    }

    public MessageReturnPojo deleteOfficialRank(Integer id){
        try {
            if (id != null && !id.equals(0)) {
                officialRankDao.deleteItem(id);
                return new MessageReturnPojo(0, "Delete was successful!");
            }else {
                throw new DoesNotExistException(-500,"Wrong number");
            }
        } catch (Exception e) {
            return new MessageReturnPojo(-1, e.getMessage());
        }
    }

    public List<OfficialRank> getOfficialRankByName(OfficialRank model){

        DetachedCriteria crit = DetachedCriteria.forClass(OfficialRank.class);

        if (model.getName() != null && !model.getName().equals("")) {
            if (model.getName().contains("*")) {
                crit.add(Restrictions.like("name", model.getName().replaceAll("\\*", "\\%")).ignoreCase());
            } else {
                crit.add(Restrictions.like("name", "%" + model.getName() + "%").ignoreCase());
            }
        }
        return officialRankDao.list(crit);
    }


}
