package net.inference.database;

import java.util.Iterator;
import java.util.List;

import com.j256.ormlite.dao.Dao;

/**
 * @author gzheyts
 */
public interface IBaseApi<T, ID>
{

	List<T> findAll();

	long count();

	T findById(ID id);

	Iterator<T> getIteratorByProperty(String propertyName, boolean propertyValue);

	List<T> findByProperty(String propertyName, String propertyValue);

	List<T> findByPropertyAndModifiedProperty(String propertyName, String propertyValue, String modifier);

	List<T> findByProperties(String firstProperty, String firstValue, String secondProperty, String secondValue);

	List<T> findByProperties(String firstProperty, int firstValue, String secondProperty, int secondValue);

	<V> void changeProperty(long id, String propertyName, V propertyValue);

	ID id(T obj);

	boolean create(T obj);

	boolean delete(T obj);

	boolean deleteById(ID id);


	boolean exists(ID id);

	Dao<T, ID> getDao();


}
