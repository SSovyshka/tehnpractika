/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sim.data.dao;


import sim.data.interfaces.IGenericDao;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.TypedQuery;
import org.hibernate.Session;
import org.hibernate.HibernateException;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.engine.spi.SessionImplementor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author dit51
 */
public abstract class GenericDaoHibernateImpl<T, PK extends Serializable> implements IGenericDao<T, PK> {
 //private static final Logger logger = LogManager.getLogger(GenericDaoHibernateImpl.class);
 
    protected Class<T> entityClass;
    
    //@Autowired
    //private SessionFactory sessionFactory;
    
    /**
    * An EntityManager will be automatically injected from entityManagerFactory
    */
    @PersistenceContext(name = "SIM")
    @Qualifier(value = "EntityManagerPg")
    protected EntityManager em;
    
//    public GenericDaoHibernateImpl() {
//     
//         this.entityClass = (Class<T>) getClass();
//        //this.entityClass = (Class<T>) genericSuperclass.getActualTypeArguments()[0];
//        
//    }

    public GenericDaoHibernateImpl() {
    }

   
    public GenericDaoHibernateImpl(Class<T> type) {
        this.entityClass = type;
    }

    
    /**
    * Persist the entity object into database
    */
    @Override
    public T create(T entity) {
        this.em.persist(entity);
        this.em.flush();
        return entity;
    }
   
//    public PK create(T o) {
//        return (PK) getSession().save(o);
//    }

    @Override
    public T read(PK id) {
        return this.em.find(entityClass, id);
    }

//    public T read(PK id) {
//        return (T) getSession().get(type, id);
//    }

    
    
    /**
    * Save changes made to a persistent object.
    */
    @Override
    public T update(T entity) {
        return this.em.merge(entity);
    }

    
//    public void update(T o) {
//        getSession().update(o);
//    }

    
    
    /**
     * Remove an object from persistent storage in the database
     */
    @Override
    public void delete(T entity) {
        entity = this.em.merge(entity);
        this.em.remove(entity);
    }
    
    
    public Session getSession() {
        Session session = em.unwrap(SessionImplementor.class);
        return session;
    }
    
    
    //get list by criteria
    @Transactional(readOnly = true)
    public List<T> list(DetachedCriteria crit){
        List<T> list = crit.getExecutableCriteria(getSession()).list();
        //closeSession();
        return list;
    }
    
    
    @Transactional(readOnly = true)
    public T getFirst(DetachedCriteria crit){
        List<T> resultSet =  crit.getExecutableCriteria(getSession()).list();
        if(resultSet != null && !resultSet.isEmpty()){
           return resultSet.get(0);
        } 
        return null;
    }

    
    //get list by namedQuery
    public List<T> list(String namedQuery) {
        
        List<T> list = em.createNamedQuery(namedQuery).getResultList();
       
//        Query query = getSession().getNamedQuery(namedQuery);
//        List<T> list = query.list();
//        closeSession();
        return list;
    }
    
    //get list by namedQuery with params
    public List<T> list(String namedQuery, Map<String, ?> parameters) {
        TypedQuery<T> query = em.createNamedQuery(
                            namedQuery,entityClass);
        for (Map.Entry<String, ?> entry : parameters.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());
        }
        List<T> list = query.getResultList();

        //closeSession();
        return list;
    }
/**
 * for run procedure
 * @param procedure
 * @param parameters
 * @return 
 */
     public boolean runprocedure(String procedure, Map<String, ?> parameters) {
    try{    
        StoredProcedureQuery storedProcedure = em.createNamedStoredProcedureQuery(procedure);
            for (Map.Entry<String, ?> entry : parameters.entrySet()) {
            storedProcedure.setParameter(entry.getKey(), entry.getValue());
        }

         storedProcedure.execute();
        } 
        catch (HibernateException e) {
            //logger.error(e);
            return false;
        }
        return true;
    
     
    }
     
/**
 * for run procedure
 * @param namedQuery
 * @param parameters
 * @return 
 */
     public boolean runNamedQuery(String namedQuery, Map<String, ?> parameters) {
    try{    
        TypedQuery<T> query = em.createNamedQuery(
                            namedQuery,entityClass);
        parameters.entrySet().forEach((entry) -> {
            query.setParameter(entry.getKey(), entry.getValue());
        });
           query.executeUpdate();
         
        } 
        catch (HibernateException e) {
            return false;
        }
        return true;
    
     
    }
    /**
     * Переопределите данный метод
     * @return List<T>
     */
    @Override
    public List<T> findAll()
    {
        return this.em.createQuery("from " + this.entityClass.getSimpleName()).getResultList();
    }
     
}
