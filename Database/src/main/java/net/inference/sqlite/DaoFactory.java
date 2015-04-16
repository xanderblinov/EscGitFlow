package net.inference.sqlite;

import java.sql.SQLException;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

/**
 * @author gzheyts
 */
public class DaoFactory {

    /**
     * Create and return a Dao based on the arguments.
     */
    public static <T, ID> Dao<T, ID> createDao(ConnectionSource connectionSource, Class<T> clazz) throws SQLException {
        return DaoManager.createDao(connectionSource, clazz);
    }

    /**
     * Create and return a Dao based on the arguments.
     */
    public static <T, ID> Dao<T, ID> createDao(ConnectionSource connectionSource, DatabaseTableConfig<T> tableConfig)
            throws SQLException {
        return DaoManager.createDao(connectionSource, tableConfig);
    }
}
