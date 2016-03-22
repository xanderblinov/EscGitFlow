import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import net.inference.Config;
import net.inference.sqlite.DatabaseApi;
import net.inference.sqlite.dto.Term;
import net.inference.sqlite.dto.TermToTerm;

/**
 * Created by Мария on 22.03.16.
 */
public class DoFreqAnalysis
{
	public static void main(String[] args)throws ClassNotFoundException, SQLException, IOException{

		//write in file usage statistics by year for every term

		DatabaseApi api = new DatabaseApi(Config.Database.TEST, false);

		final List<Term> termList = api.term().findAll();
		List<FrequencyAnalysis> anList = new ArrayList<>();

		long start = System.currentTimeMillis();

		for(int i = 0; i < termList.size(); i++){
			anList.add(new FrequencyAnalysis(termList, termList.get(i).getValue()));
		}

		api.onStop();

		long finish = System.currentTimeMillis();
		long timeConsumedMillis = finish - start;
		System.out.println("\nTime is: " + timeConsumedMillis);

		FileWriter freq = new FileWriter("freq.txt", false);
		for(int i = 0; i < anList.size(); i++)
			freq.write(anList.get(i).toString());
		freq.flush();

	}


}
