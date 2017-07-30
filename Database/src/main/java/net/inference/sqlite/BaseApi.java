package net.inference.sqlite;

import java.sql.SQLException;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.logger.Logger;
import com.j256.ormlite.logger.LoggerFactory;
import net.inference.database.IBaseApi;
import net.inference.database.IDatabaseApi;

/**
 * @author gzheyts
 */

public class BaseApi<T,ID> implements IBaseApi<T,ID>
{
    private Dao<T,ID> dao;
    private static Logger logger = LoggerFactory.getLogger(BaseApi.class);


    private Class<T> clazz;
    private IDatabaseApi databaseApi;

    public BaseApi(IDatabaseApi api, Class<T> cls) {
        databaseApi = api;
        clazz = cls;
    }

    @Override
    public List<T> findAll() {
        try {
            return getDao().queryForAll();
        } catch (SQLException e) {
            logger.error(e, "");
        }

        return Collections.emptyList();
    }

    @Override
    public T findById(ID id) {
        try {
            return getDao().queryForId(id);
        } catch (SQLException e) {
            logger.error(e,"");
        }
        return null;
    }

    @Override
    public List<T> findByProperty(String propertyName, String propertyValue) {
        try {
            return getDao().queryForEq(propertyName, propertyValue);
        } catch (SQLException e) {
            logger.error(e, "");
        }

        return Collections.emptyList();
    }

    @Override
    public Iterator<T> getIterator(){
        return getDao().iterator();
    }

    @Override
    public ID id(T obj) {
        try {
            return getDao().extractId(obj);
        } catch (SQLException e) {
            logger.error("fail to extract id", e);
        }
        return null;
    }

    @Override
    public boolean create(T obj) {

        try {
            return getDao().create(obj)== 1;
        } catch (SQLException e) {
            logger.error(e, "");
        }
        return false;
    }


    @Override
    public boolean delete(T obj) {
        try {
            return getDao().delete(obj) == 1;
        } catch (SQLException e) {
            logger.error("", e);
        }

        return false;
    }

    @Override
    public boolean deleteById(ID id) {
        try {
            return getDao().deleteById(id) == 1;
        } catch (SQLException e) {
            logger.error("", e);

        }
        return false;
    }

    @Override
    public boolean exists(ID id) {
        try {
            return getDao().idExists(id);
        } catch (SQLException e) {
            logger.error("", e);
        }

        return false;
    }


    public Dao<T, ID> getDao() {
        if (dao == null) {
            try {
                this.dao = DaoManager.createDao(databaseApi.getConnection(), clazz);
            } catch (SQLException e) {
                logger.error("fail to create dao for class : " + clazz);
            }

        }
        return dao;
    }
}
