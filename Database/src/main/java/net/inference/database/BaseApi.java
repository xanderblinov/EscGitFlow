package net.inference.database;

import java.util.List;

/**
 * @author gzheyts
 */
public interface BaseApi<T, ID> {

    List<T> findAll();

    T findById(ID id);

    List<T> findByProperty(String propertyName, String propertyValue);

    ID id(T obj);

    boolean create(T obj);

    boolean delete(T obj);

    boolean deleteById(ID id);


    boolean exists(ID id);




}
