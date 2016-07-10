import net.inference.Config;
import net.inference.sqlite.DatabaseApi;
import net.inference.sqlite.dto.Term;
import net.inference.sqlite.dto.TermToTerm;


import java.io.FileWriter;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by M.Pankova on 26.02.16.
 */
public class TermCluster
{
	public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException
	{
		DatabaseApi api = new DatabaseApi(Config.Database.TEST, false);

		api.onStart();

		TimeBorder time = new TimeBorder();
		double range = 28.0;

		final List<TermToTerm> termToTermList = api.termToTerm().findAll();
		final List<Term> termList = api.term().findAll();
		api.onStop();


		FileWriter id_link = new FileWriter("id_link" + time.getStart() + "_" + time.getFinish() + ".txt", false);
		int count = 0;
		List<TermCrutch> crutches = new ArrayList<>();
		for(int i = 0; i < termList.size(); i++)
		{
			if (termList.get(i).getCount() > range)
			{
				TermCrutch crutch = new TermCrutch(count, termList.get(i).getId());
				crutches.add(crutch);
				id_link.write(crutch.toString());
				count++;
			}
		}
		id_link.flush();
		//составили список соответствия реальных и фейковых айдишников от 1


		FileWriter gr = new FileWriter("graph" + time.getStart() + "_" + time.getFinish() + ".txt", false);

		FileWriter in = new FileWriter("inputSlm" + time.getStart() + "_" + time.getFinish() + ".txt", false);

		for(TermToTerm link : termToTermList)
		{
			int from = findFakeId(crutches, link.getFrom().getId());
			int to = findFakeId(crutches, link.getTo().getId());
			in.write(from + "\t" + to + "\t" + link.getCount() + "\n");

			String fromName = termList.get(link.getFrom().getId()-1).getValue();
			String toName = termList.get(link.getTo().getId()-1).getValue();
			gr.write(fromName + "\t" + toName + "\t" + link.getCount() + "\n");

		}
		gr.flush();
		in.flush();

	}

	public static int findFakeId(List<TermCrutch> list, int id)
	{
		for(int i = 0; i < list.size(); i++)
		{
			if(list.get(i).getRealId() == id)
				return list.get(i).getFakeId();
		}
		return -1;
	}


}



