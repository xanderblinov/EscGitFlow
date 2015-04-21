package net.inference.sqlite;

import net.inference.database.EvolutionApi;
import net.inference.sqlite.dto.EvolutionImpl;

/**
 * @author gzheyts
 */
public class EvolutionApiImpl extends BaseApiImpl<EvolutionImpl, Integer> implements EvolutionApi {
    private SqliteApi sqliteApi;


    public EvolutionApiImpl(SqliteApi sqliteApi) {
        super(sqliteApi,EvolutionImpl.class);
        this.sqliteApi = sqliteApi;
    }

}
