/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sim.data.dao;


import sim.data.interfaces.IGenericFnDao;
import sim.utils.OperationParameter;
import com.vladmihalcea.hibernate.type.array.DateArrayType;
import com.vladmihalcea.hibernate.type.array.DecimalArrayType;
import com.vladmihalcea.hibernate.type.array.IntArrayType;
import com.vladmihalcea.hibernate.type.array.StringArrayType;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import javax.persistence.EntityManager;
import javax.persistence.Parameter;
import javax.persistence.PersistenceContext;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.hibernate.type.BigDecimalType;
import org.hibernate.type.BigIntegerType;
import org.hibernate.type.BooleanType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.PostgresUUIDType;
import org.hibernate.type.ShortType;
import org.hibernate.type.StringType;
import org.hibernate.type.TimestampType;

/**
 *
 * @author dit51
 */
public abstract class GenericDaoFnHibernateImpl<T> implements IGenericFnDao<T>{

    
    protected Class<T> entityClass;
    
    /**
    * An EntityManager will be automatically injected from entityManagerFactory
    */
    @PersistenceContext(name = "BSP")
    protected EntityManager em;
    

    public GenericDaoFnHibernateImpl() {
    }

   
    public GenericDaoFnHibernateImpl(Class<T> type) {
        this.entityClass = type;
    }

    
    public Session getSession() {
        Session session = em.unwrap(Session.class);
        return session;
    }
    
    @Override
    public List<T> runFunction(String functionName) {
        
        String sql = "SELECT * FROM "+ functionName+"()";
        Query query = (NativeQuery) getSession().createNativeQuery(sql).addEntity(this.entityClass);
        
        List<T> result = query.list();
        return result;
    }


    @Override
    public T runFunctionSingleResult(String functionName) {
        
        String sql = "SELECT * FROM "+ functionName+"()";
        Query query = (NativeQuery) getSession().createNativeQuery(sql);
        
        return (T) query.getSingleResult();
    }
    
    
    @Override
    public Integer runFunction(String functionName, Map<String, ?> parameters) {
        
        String sql = "SELECT * FROM "+ functionName+"(";
        
        int i = 0;
        for (Map.Entry<String, ?> entry : parameters.entrySet()) {
            sql = ( i == 0 ? sql.concat(":").concat(entry.getKey()) : sql.concat(",:").concat(entry.getKey()));
            i++;
        }
        sql = sql +")";
        
       
        Query query = (NativeQuery) getSession().createNativeQuery(sql);
        for (Map.Entry<String, ?> entry : parameters.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());
        }

        Integer result = (Integer) query.getSingleResult();
        
        return result;
        
    }
    
    
    @Override
    public List<T> runFunction(String functionName, Map<String, ?> parameters, String orderBy) {
        
        String sql = "SELECT * FROM "+ functionName+"(";
        
        int i = 0;
        for (Map.Entry<String, ?> entry : parameters.entrySet()) {
            sql = ( i == 0 ? sql.concat(":").concat(entry.getKey()) : sql.concat(",:").concat(entry.getKey()));
            i++;
        }
        sql = sql +")";
        
        if(!orderBy.isEmpty()){
           sql = sql + " order by " + orderBy;
        }
        
        Query query = (NativeQuery) getSession().createNativeQuery(sql)
                                                      .addEntity(this.entityClass);
        for (Map.Entry<String, ?> entry : parameters.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());
        }

        List result = query.list();
        
        return result;
        
    }

    
    @Override
    public T runNamedParamFunction(String functionName, Map<Parameter, ?> parameters) {
        
        String sql = "SELECT * FROM "+ functionName+"(";
        
        int i = 0;
        for (Map.Entry<Parameter, ?> entry : parameters.entrySet()) {
            sql = ( i == 0 ? sql.concat(":").concat(entry.getKey().getName()) : sql.concat(",:").concat(entry.getKey().getName()));
            i++;
        }
        sql = sql +")";
        
        
        Query query = (NativeQuery) getSession().createNativeQuery(sql);
        for (Map.Entry<Parameter, ?> entry : parameters.entrySet()) {
            Parameter param = entry.getKey();
            
                   
            if(param.getParameterType() == Integer.class){
               query.setParameter(param.getName(), entry.getValue(), IntegerType.INSTANCE);
            } else if(param.getParameterType() == String.class){
               query.setParameter(param.getName(), entry.getValue(), StringType.INSTANCE);
            } else if(param.getParameterType() == Date.class){
               query.setParameter(param.getName(), entry.getValue(), TimestampType.INSTANCE);
            } else if(param.getParameterType() == Short.class){
               query.setParameter(param.getName(), entry.getValue(), ShortType.INSTANCE);
            } else if(param.getParameterType() == BigInteger.class){
               query.setParameter(param.getName(), entry.getValue(), BigIntegerType.INSTANCE);
            } else if(param.getParameterType() == BigDecimal.class){
               query.setParameter(param.getName(), entry.getValue(), BigDecimalType.INSTANCE);
            } else if(param.getParameterType() == Boolean.class){
               query.setParameter(param.getName(), entry.getValue(), BooleanType.INSTANCE);
            } else if(param.getParameterType() == UUID.class){
               query.setParameter(param.getName(), entry.getValue(), PostgresUUIDType.INSTANCE);  
            // МАССИВ В ПАРАМЕТРЕ !!!!   
            } else if(param.getParameterType() == Integer[].class){
               query.setParameter(param.getName(), entry.getValue(), IntArrayType.INSTANCE);    
            } else if(param.getParameterType() == String[].class){
               query.setParameter(param.getName(), entry.getValue(), StringArrayType.INSTANCE);    
            } 
            
            
        }

        //Integer result = (Integer) query.getSingleResult();
        //return result;

        return (T) query.getSingleResult();
        
    }

    @Override
    public List<T> runNamedParamFunction(String functionName, Map<Parameter, ?> parameters, String orderBy) {
        
        String sql = "SELECT * FROM "+ functionName+"(";
        
        int i = 0;
        for (Map.Entry<Parameter, ?> entry : parameters.entrySet()) {
            sql = ( i == 0 ? sql.concat(":").concat(entry.getKey().getName()) : sql.concat(",:").concat(entry.getKey().getName()));
            i++;
        }
        sql = sql +")";
        
        if(!orderBy.isEmpty()){
           sql = sql + " order by " + orderBy;
        }
        
        Query query = (NativeQuery) getSession().createNativeQuery(sql)
                                                      .addEntity(this.entityClass);
        for (Map.Entry<Parameter, ?> entry : parameters.entrySet()) {
            Parameter param = entry.getKey();
            
                   
            if(param.getParameterType() == Integer.class){
               query.setParameter(param.getName(), entry.getValue(), IntegerType.INSTANCE);
            } else if(param.getParameterType() == String.class){
               query.setParameter(param.getName(), entry.getValue(), StringType.INSTANCE);
            } else if(param.getParameterType() == Date.class){
               query.setParameter(param.getName(), entry.getValue(), TimestampType.INSTANCE);
            } else if(param.getParameterType() == Short.class){
               query.setParameter(param.getName(), entry.getValue(), ShortType.INSTANCE);
            } else if(param.getParameterType() == BigInteger.class){
               query.setParameter(param.getName(), entry.getValue(), BigIntegerType.INSTANCE);
            } else if(param.getParameterType() == BigDecimal.class){
               query.setParameter(param.getName(), entry.getValue(), BigDecimalType.INSTANCE);
            } else if(param.getParameterType() == Boolean.class){
               query.setParameter(param.getName(), entry.getValue(), BooleanType.INSTANCE);
            } else if(param.getParameterType() == UUID.class){
               query.setParameter(param.getName(), entry.getValue(), PostgresUUIDType.INSTANCE);    
            // МАССИВ В ПАРАМЕТРЕ !!!!   
            } else if(param.getParameterType() == Integer[].class){
               query.setParameter(param.getName(), entry.getValue(), IntArrayType.INSTANCE);    
            } else if(param.getParameterType() == String[].class){
               query.setParameter(param.getName(), entry.getValue(), StringArrayType.INSTANCE);    
            }  
            
            
        }

        List result = query.list();
        
        return result;
        
    }


    @Override
    public List<T> runNamedParamFunction(String functionName, String fields, Map<Parameter, ?> parameters, String orderBy) {
        
        String sql = "SELECT "+ fields +" FROM "+ functionName+"(";
        
        int i = 0;
        for (Map.Entry<Parameter, ?> entry : parameters.entrySet()) {
            sql = ( i == 0 ? sql.concat(":").concat(entry.getKey().getName()) : sql.concat(",:").concat(entry.getKey().getName()));
            i++;
        }
        sql = sql +")";
        
        if(!orderBy.isEmpty()){
           sql = sql + " order by " + orderBy;
        }
        
        Query query = (NativeQuery) getSession().createNativeQuery(sql)
                                                      .addEntity(this.entityClass);
        for (Map.Entry<Parameter, ?> entry : parameters.entrySet()) {
            Parameter param = entry.getKey();
            
                   
            if(param.getParameterType() == Integer.class){
               query.setParameter(param.getName(), entry.getValue(), IntegerType.INSTANCE);
            } else if(param.getParameterType() == String.class){
               query.setParameter(param.getName(), entry.getValue(), StringType.INSTANCE);
            } else if(param.getParameterType() == Date.class){
               query.setParameter(param.getName(), entry.getValue(), TimestampType.INSTANCE);
            } else if(param.getParameterType() == Short.class){
               query.setParameter(param.getName(), entry.getValue(), ShortType.INSTANCE);
            } else if(param.getParameterType() == BigInteger.class){
               query.setParameter(param.getName(), entry.getValue(), BigIntegerType.INSTANCE);
            } else if(param.getParameterType() == BigDecimal.class){
               query.setParameter(param.getName(), entry.getValue(), BigDecimalType.INSTANCE);
            } else if(param.getParameterType() == Boolean.class){
               query.setParameter(param.getName(), entry.getValue(), BooleanType.INSTANCE);
            } else if(param.getParameterType() == UUID.class){
               query.setParameter(param.getName(), entry.getValue(), PostgresUUIDType.INSTANCE);    
            // МАССИВ В ПАРАМЕТРЕ !!!!   
            } else if(param.getParameterType() == Integer[].class){
               query.setParameter(param.getName(), entry.getValue(), IntArrayType.INSTANCE);    
            } else if(param.getParameterType() == String[].class){
               query.setParameter(param.getName(), entry.getValue(), StringArrayType.INSTANCE);    
            }  
            
            
        }

        List result = query.list();
        
        return result;
        
    }
    
    @Override
    public List<T> runNamedParamFunction(String functionName, String sqlResultSetMappingName, String fields, Map<Parameter, ?> parameters, String orderBy) {
        
        String sql = "SELECT "+ fields +" FROM "+ functionName+"(";
        
        int i = 0;
        for (Map.Entry<Parameter, ?> entry : parameters.entrySet()) {
            sql = ( i == 0 ? sql.concat(":").concat(entry.getKey().getName()) : sql.concat(",:").concat(entry.getKey().getName()));
            i++;
        }
        sql = sql +")";
        
        if(!orderBy.isEmpty()){
           sql = sql + " order by " + orderBy;
        }
        
        Query query = (NativeQuery) getSession().createNativeQuery(sql,sqlResultSetMappingName);
        for (Map.Entry<Parameter, ?> entry : parameters.entrySet()) {
            Parameter param = entry.getKey();
            
                   
            if(param.getParameterType() == Integer.class){
               query.setParameter(param.getName(), entry.getValue(), IntegerType.INSTANCE);
            } else if(param.getParameterType() == String.class){
               query.setParameter(param.getName(), entry.getValue(), StringType.INSTANCE);
            } else if(param.getParameterType() == Date.class){
               query.setParameter(param.getName(), entry.getValue(), TimestampType.INSTANCE);
            } else if(param.getParameterType() == Short.class){
               query.setParameter(param.getName(), entry.getValue(), ShortType.INSTANCE);
            } else if(param.getParameterType() == BigInteger.class){
               query.setParameter(param.getName(), entry.getValue(), BigIntegerType.INSTANCE);
            } else if(param.getParameterType() == BigDecimal.class){
               query.setParameter(param.getName(), entry.getValue(), BigDecimalType.INSTANCE);
            } else if(param.getParameterType() == Boolean.class){
               query.setParameter(param.getName(), entry.getValue(), BooleanType.INSTANCE);
            } else if(param.getParameterType() == UUID.class){
               query.setParameter(param.getName(), entry.getValue(), PostgresUUIDType.INSTANCE);    
            // МАССИВ В ПАРАМЕТРЕ !!!!   
            } else if(param.getParameterType() == Integer[].class){
               query.setParameter(param.getName(), entry.getValue(), IntArrayType.INSTANCE);    
            } else if(param.getParameterType() == String[].class){
               query.setParameter(param.getName(), entry.getValue(), StringArrayType.INSTANCE);    
            } else if(param.getParameterType() == BigDecimal[].class){
               query.setParameter(param.getName(), entry.getValue(), DecimalArrayType.INSTANCE);   
            } else if(param.getParameterType() == Date[].class){
               query.setParameter(param.getName(), entry.getValue(), DateArrayType.INSTANCE);    
            } 
            
            
        }

        List result = query.list();
        
        return result;
        
    }

    
    
    /*
    Запуск процедуры с указанием условия выбора типа 
    Пример использования FinancialDaoService.getVCustomerPaymentgroupByPayment
    */
    @Override
    public List<T> runNamedParamFunction(String functionName, String fields, Map<Parameter, ?> parameters, Map<Parameter, OperationParameter> whereparameters, String orderBy) {
        
        String sql = "SELECT "+ fields +" FROM "+ functionName+"(";
        
        int i = 0;
        for (Map.Entry<Parameter, ?> entry : parameters.entrySet()) {
            sql = ( i == 0 ? sql.concat("?") : sql.concat(",?").concat(entry.getKey().getName()));
            i++;
        }
        sql = sql +")";


        i = 0;
        Map<Parameter, Object> whereparams = new LinkedHashMap<>();  //сохраняет порядок элементов
        if (!whereparameters.isEmpty()) {
          sql = sql +" where "; 
           for (Map.Entry<Parameter, ?> whereentry : whereparameters.entrySet()) {
                sql = ( i == 0 ? sql.concat(whereentry.getKey().getName()).concat(((OperationParameter)whereentry.getValue()).getOperation()).concat("?") : 
                                 sql.concat(" and ").concat(whereentry.getKey().getName()).concat(((OperationParameter)whereentry.getValue()).getOperation()).concat("?"));
                i++;
                whereparams.put(whereentry.getKey(),((OperationParameter)whereentry.getValue()).getParamValue());
           }
        }
        
        if(!orderBy.isEmpty()){
           sql = sql + " order by " + orderBy;
        }
        
        Query query = (NativeQuery) getSession().createNativeQuery(sql)
                                                      .addEntity(this.entityClass);
        setQueryParametersByPosition(query, parameters);
        setQueryParametersByPosition(query, whereparams);

        List result = query.list();
        
        return result;
        
    }


    /*
    Запуск процедуры с указанием условия выбора типа 
    Пример использования FinancialDaoService.getVCustomerPaymentgroupByPayment
    */
    @Override
    public List<T> runNamedParamFunction(String functionName, String sqlResultSetMappingName, String fields, Map<Parameter, ?> parameters, Map<Parameter, OperationParameter> whereparameters, String orderBy) {
        
        String sql = "SELECT "+ fields +" FROM "+ functionName+"(";
        
        int i = 0;
        for (Map.Entry<Parameter, ?> entry : parameters.entrySet()) {
            sql = ( i == 0 ? sql.concat("?") : sql.concat(",?"));
            i++;
        }
        sql = sql +")";


        i = 0;
        Map<Parameter, Object> whereparams = new LinkedHashMap<>();  //сохраняет порядок элементов
        if (!whereparameters.isEmpty()) {
          sql = sql +" where "; 
           for (Map.Entry<Parameter, ?> whereentry : whereparameters.entrySet()) {
                sql = ( i == 0 ? sql.concat(whereentry.getKey().getName()).concat(((OperationParameter)whereentry.getValue()).getOperation()).concat("?") : 
                                 sql.concat(" and ").concat(whereentry.getKey().getName()).concat(((OperationParameter)whereentry.getValue()).getOperation()).concat("?"));
                i++;
                whereparams.put(whereentry.getKey(),((OperationParameter)whereentry.getValue()).getParamValue());
           }
        }
        
        if(!orderBy.isEmpty()){
           sql = sql + " order by " + orderBy;
        }
        
        Query query = (NativeQuery) getSession().createNativeQuery(sql,sqlResultSetMappingName);
                                                      
        setQueryParametersByPosition(query, parameters);
        setQueryParametersByPosition(query, whereparams);

        List result = query.list();
        
        return result;
        
    }

    private void setQueryParametersByPosition(Query query, Map<Parameter, ?> parameters){


        for (Map.Entry<Parameter, ?> entry : parameters.entrySet()) {
            Parameter param = entry.getKey();
            
                   
            if(param.getParameterType() == Integer.class){
               query.setParameter(param.getPosition(), entry.getValue(), IntegerType.INSTANCE);
            } else if(param.getParameterType() == String.class){
               query.setParameter(param.getPosition(), entry.getValue(), StringType.INSTANCE);
            } else if(param.getParameterType() == Date.class){
               query.setParameter(param.getPosition(), entry.getValue(), TimestampType.INSTANCE);
            } else if(param.getParameterType() == Short.class){
               query.setParameter(param.getPosition(), entry.getValue(), ShortType.INSTANCE);
            } else if(param.getParameterType() == BigInteger.class){
               query.setParameter(param.getPosition(), entry.getValue(), BigIntegerType.INSTANCE);
            } else if(param.getParameterType() == BigDecimal.class){
               query.setParameter(param.getPosition(), entry.getValue(), BigDecimalType.INSTANCE);
            } else if(param.getParameterType() == Boolean.class){
               query.setParameter(param.getPosition(), entry.getValue(), BooleanType.INSTANCE);
            } else if(param.getParameterType() == UUID.class){
               query.setParameter(param.getPosition(), entry.getValue(), PostgresUUIDType.INSTANCE);    
            // МАССИВ В ПАРАМЕТРЕ !!!!   
            } else if(param.getParameterType() == Integer[].class){
               query.setParameter(param.getName(), entry.getValue(), IntArrayType.INSTANCE);    
            } else if(param.getParameterType() == String[].class){
               query.setParameter(param.getName(), entry.getValue(), StringArrayType.INSTANCE);    
            }  
        }
        
        
    }

    @Override
    public Query prepareFunction(String functionName, Set<String> parameters, String orderBy) {
        
        String sql = "SELECT * FROM "+ functionName+"(";
        
        int i = 0;
        for (String param : parameters) {
            sql = ( i == 0 ? sql.concat(":").concat(param) : sql.concat(",:").concat(param));
            i++;
        }
        sql = sql +")";
        
        if(!orderBy.isEmpty()){
           sql = sql + " order by " + orderBy;
        }
        
        Query query = (NativeQuery) getSession().createNativeQuery(sql)
                                                      .addEntity(this.entityClass);
       
        return query;
        
    }

    
    /**
     * Запуск функции 
     * @param functionName - название функции
     * @param parameters - параметры
     * @return одиночный объект {}
     */
    @Override
    public T runNamedParamSingleObjectReturnFunction(String functionName, Map<Parameter, ?> parameters) {
        
        String sql = "SELECT * FROM "+ functionName+"(";
        
        int i = 0;
        for (Map.Entry<Parameter, ?> entry : parameters.entrySet()) {
            sql = ( i == 0 ? sql.concat(":").concat(entry.getKey().getName()) : sql.concat(",:").concat(entry.getKey().getName()));
            i++;
        }
        sql = sql +")";
        
        
        Query query = (NativeQuery) getSession().createNativeQuery(sql).addEntity(this.entityClass);
        for (Map.Entry<Parameter, ?> entry : parameters.entrySet()) {
            Parameter param = entry.getKey();
            
                   
            if(param.getParameterType() == Integer.class){
               query.setParameter(param.getName(), entry.getValue(), IntegerType.INSTANCE);
            } else if(param.getParameterType() == String.class){
               query.setParameter(param.getName(), entry.getValue(), StringType.INSTANCE);
            } else if(param.getParameterType() == Date.class){
               query.setParameter(param.getName(), entry.getValue(), TimestampType.INSTANCE);
            } else if(param.getParameterType() == Short.class){
               query.setParameter(param.getName(), entry.getValue(), ShortType.INSTANCE);
            } else if(param.getParameterType() == BigInteger.class){
               query.setParameter(param.getName(), entry.getValue(), BigIntegerType.INSTANCE);
            } else if(param.getParameterType() == BigDecimal.class){
               query.setParameter(param.getName(), entry.getValue(), BigDecimalType.INSTANCE);
            } else if(param.getParameterType() == Boolean.class){
               query.setParameter(param.getName(), entry.getValue(), BooleanType.INSTANCE);
            } else if(param.getParameterType() == UUID.class){
               query.setParameter(param.getName(), entry.getValue(), PostgresUUIDType.INSTANCE);  
            // МАССИВ В ПАРАМЕТРЕ !!!!   
            } else if(param.getParameterType() == Integer[].class){
               query.setParameter(param.getName(), entry.getValue(), IntArrayType.INSTANCE);    
            } else if(param.getParameterType() == String[].class){
               query.setParameter(param.getName(), entry.getValue(), StringArrayType.INSTANCE);    
            } 
            
            
        }

        //Integer result = (Integer) query.getSingleResult();
        //return result;
        try{
        T result = (T) query.getSingleResult();
            return result;
        }catch(Exception e){
            return null;
        } 
        
    }

    /**
     * Запуск функции 
     * T - возвращаемый тип данных, pojo class (не entity)
     * @param functionName - название функции
     * @param parameters - параметры
     * @param sqlResultSetMappingName - имя маппинга
     * @return одиночный объект
     */
    @Override
    public T runNamedParamSingleObjectReturnFunction(String functionName, String sqlResultSetMappingName, Map<Parameter, ?> parameters) {
        
        String sql = "SELECT * FROM "+ functionName+"(";
        
        int i = 0;
        for (Map.Entry<Parameter, ?> entry : parameters.entrySet()) {
            sql = ( i == 0 ? sql.concat(":").concat(entry.getKey().getName()) : sql.concat(",:").concat(entry.getKey().getName()));
            i++;
        }
        sql = sql +")";
        
        
        Query query = (NativeQuery) getSession().createNativeQuery(sql,sqlResultSetMappingName);
        for (Map.Entry<Parameter, ?> entry : parameters.entrySet()) {
            Parameter param = entry.getKey();
            
                   
            if(param.getParameterType() == Integer.class){
               query.setParameter(param.getName(), entry.getValue(), IntegerType.INSTANCE);
            } else if(param.getParameterType() == String.class){
               query.setParameter(param.getName(), entry.getValue(), StringType.INSTANCE);
            } else if(param.getParameterType() == Date.class){
               query.setParameter(param.getName(), entry.getValue(), TimestampType.INSTANCE);
            } else if(param.getParameterType() == Short.class){
               query.setParameter(param.getName(), entry.getValue(), ShortType.INSTANCE);
            } else if(param.getParameterType() == BigInteger.class){
               query.setParameter(param.getName(), entry.getValue(), BigIntegerType.INSTANCE);
            } else if(param.getParameterType() == BigDecimal.class){
               query.setParameter(param.getName(), entry.getValue(), BigDecimalType.INSTANCE);
            } else if(param.getParameterType() == Boolean.class){
               query.setParameter(param.getName(), entry.getValue(), BooleanType.INSTANCE);
            } else if(param.getParameterType() == UUID.class){
               query.setParameter(param.getName(), entry.getValue(), PostgresUUIDType.INSTANCE);  
            // МАССИВ В ПАРАМЕТРЕ !!!!   
            } else if(param.getParameterType() == Integer[].class){
               query.setParameter(param.getName(), entry.getValue(), IntArrayType.INSTANCE);    
            } else if(param.getParameterType() == String[].class){
               query.setParameter(param.getName(), entry.getValue(), StringArrayType.INSTANCE);    
            } 
            
            
        }

        //Integer result = (Integer) query.getSingleResult();
        //return result;
        try{
        T result = (T) query.getSingleResult();
            return result;
        }catch(Exception e){
            return null;
        } 
        
    }
    
}
