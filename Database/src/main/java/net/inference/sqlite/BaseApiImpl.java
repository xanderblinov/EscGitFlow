package net.inference.sqlite;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.logger.Logger;
import com.j256.ormlite.logger.LoggerFactory;
import net.inference.database.BaseApi;
import net.inference.database.DatabaseApi;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

/**
 * @author gzheyts
 */

public class BaseApiImpl<T,ID> implements BaseApi<T,ID> {
    private Dao<T,ID> dao;
    private static Logger logger = LoggerFactory.getLogger(BaseApiImpl.class);


    private Class<T> clazz;
    private DatabaseApi databaseApi;

    public BaseApiImpl(DatabaseApi api, Class<T> cls) {
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


    protected Dao<T, ID> getDao() {
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
