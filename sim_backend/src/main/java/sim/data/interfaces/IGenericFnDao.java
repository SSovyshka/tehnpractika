/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sim.data.interfaces;

import sim.utils.OperationParameter;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.persistence.Parameter;
import org.hibernate.query.Query;

/**
 *
 * @author dit51
 */
public interface IGenericFnDao<T> {

    
    /**
     * Запуск функции 
     * @param functionName - название функции
     * @return List<T>
     */
    List<T> runFunction(String functionName);

    /**
     * Запуск функции 
     * @param functionName - название функции
     * @return T
     */
    T runFunctionSingleResult(String functionName);

    /**
     * Запуск функции 
     * @param functionName - название функции
     * @param parameters - параметры
     * @return T
     */
    List<T> runFunction(String functionName, Map<String, ?> parameters, String orderBy);

     /**
     * Запуск функции 
     * @param functionName - название функции
     * @param parameters - параметры
     * @return Integer
     */
    Integer runFunction(String functionName, Map<String, ?> parameters);

    /**
     * Запуск функции 
     * @param functionName - название функции
     * @param orderBy - сортировка
     */
    Query prepareFunction(String functionName, Set<String> parameters, String orderBy);

    /**
     * Запуск функции 
     * @param functionName - название функции
     * @param parameters - параметры
     * @return Integer
     */
    T runNamedParamFunction(String functionName, Map<Parameter, ?> parameters);

    /**
     * Запуск функции 
     * @param functionName - название функции
     * @param parameters - параметры
     * @return T
     */
    List<T> runNamedParamFunction(String functionName, Map<Parameter, ?> parameters, String orderBy);

    /**
     * Запуск функции 
     * @param functionName - название функции
     * @param fields - поля выбора
     * @param parameters - параметры
     * @return T
     */
    List<T> runNamedParamFunction(String functionName, String fields, Map<Parameter, ?> parameters, String orderBy);
    
     /**
     * Запуск функции 
     * T - возвращаемый тип данных, pojo class (не entity)
     * @param functionName - название функции
     * @param sqlResultSetMappingName - имя маппинга
     * @param fields - поля выбора
     * @param parameters - параметры
     * @param orderBy - сортировка по
     * @return T
     */
    List<T> runNamedParamFunction(String functionName, String sqlResultSetMappingName, String fields, Map<Parameter, ?> parameters, String orderBy);
    
    List<T> runNamedParamFunction(String functionName, String sqlResultSetMappingName, String fields, Map<Parameter, ?> parameters, Map<Parameter, OperationParameter> whereparameters, String orderBy);

    List<T> runNamedParamFunction(String functionName, String fields, Map<Parameter, ?> parameters, Map<Parameter, OperationParameter> whereparameters, String orderBy);
    
    /**
     * Запуск функции 
     * @param functionName - название функции
     * @param parameters - параметры
     * @return одиночный объект
     */
    T runNamedParamSingleObjectReturnFunction(String functionName, Map<Parameter, ?> parameters);
    
    /**
     * Запуск функции 
     * T - возвращаемый тип данных, pojo class (не entity)
     * @param functionName - название функции
     * @param sqlResultSetMappingName - имя маппинга
     * @param parameters - параметры
     * @return одиночный объект
     */
    T runNamedParamSingleObjectReturnFunction(String functionName, String sqlResultSetMappingName, Map<Parameter, ?> parameters);
    
}
