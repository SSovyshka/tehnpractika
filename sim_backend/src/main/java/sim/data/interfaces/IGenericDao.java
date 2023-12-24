/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sim.data.interfaces;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import org.hibernate.criterion.DetachedCriteria;

/**
 *
 * @author dit51
 */
public interface IGenericDao<T, PK extends Serializable> {

    /** Сохранить объект newInstance в базе данных */
    T create(T entity);

    /** Извлечь объект, предварительно сохраненный в базе данных, используя
     *   указанный id в качестве первичного ключа
     */
    T read(PK id);

    /** Сохранить изменения, сделанные в объекте.  */
    T update(T transientObject);

    /** Удалить объект из базы данных */
    void delete(T persistentObject);
    
    List<T> list(DetachedCriteria crit);
    
    T getFirst(DetachedCriteria crit);
   
    List<T> list(String namedQuery);
    
    List<T> list(String namedQuery, Map<String, ?> parameters);
    
    /**
     * Переопределите данный метод. Должен возвращать все записи из таблицы
     * @return List<T>
     */
    List<T> findAll();
    
    /**
     * Запуск процедуры без возврата значения (возврат true/false)
     * @param procedure - название процедуры которое обьявлено в модели через
     *  (@NamedStoredProcedureQuery)
     * @param parameters - параметры
     * @return 
     */
    boolean runprocedure(String  procedure, Map<String, ?> parameters);
    
    /**
     * Запуск namedQuery без возврата значения (возврат true/false)
     * @param namedQuery - название namedQuery которое обьявлено в модели
     * @param parameters - параметры
     * @return 
     */
    boolean runNamedQuery(String  namedQuery, Map<String, ?> parameters);
  
}
