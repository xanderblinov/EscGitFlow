package net.inference.sqlite;

import net.inference.database.IEvolutionApi;
import net.inference.sqlite.dto.Evolution;

/**
 * @author gzheyts
 */
public class EvolutionApi extends BaseApi<Evolution, Integer> implements IEvolutionApi
{
    private DatabaseApi mDatabaseApi;


    public EvolutionApi(DatabaseApi databaseApi) {
        super(databaseApi,Evolution.class);
        this.mDatabaseApi = databaseApi;
    }

}
