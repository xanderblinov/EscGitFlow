package net.inference.database;

import com.j256.ormlite.dao.Dao;

import java.util.List;

/**
 * @author gzheyts
 */
public interface IBaseApi<T, ID> {

    List<T> findAll();

	long count();

    T findById(ID id);

    List<T> findByProperty(String propertyName, String propertyValue);

	List<T> findByProperties(String firstProperty, String firstValue, String secondProperty, String secondValue);

	List<T> findByProperties(String firstProperty, int firstValue, String secondProperty, int secondValue);

	void changeProperty(long id, String propertyName, int propertyValue);

    ID id(T obj);

    boolean create(T obj);

    boolean delete(T obj);

    boolean deleteById(ID id);


    boolean exists(ID id);

	Dao<T, ID> getDao();


}
