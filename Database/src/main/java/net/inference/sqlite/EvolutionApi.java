package net.inference.sqlite;

import net.inference.database.IEvolutionApi;
import net.inference.sqlite.dto.Evolution;

/**
 * @author gzheyts
 */
public class EvolutionApi extends BaseApi<Evolution, Integer> implements IEvolutionApi
{
    private DatagbasseApi mDatagbasseApi;


    public EvolutionApi(DatagbasseApi datagbasseApi) {
        super(datagbasseApi,Evolution.class);
        this.mDatagbasseApi = datagbasseApi;
    }

}
