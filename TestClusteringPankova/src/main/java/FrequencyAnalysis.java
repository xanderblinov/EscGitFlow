import java.util.ArrayList;
import java.util.List;

import net.inference.sqlite.DatabaseApi;
import net.inference.sqlite.dto.Term;

/**
 * Created by Мария on 15.03.16.
 */
public class FrequencyAnalysis
{
	//for every term we get its name and list<year, count of using>
	private String actTerm;
	private List<Frequency> usingForYears = new ArrayList<>();


	FrequencyAnalysis(DatabaseApi api, String term){
		actTerm = term;
		for(int i = 1; i <= api.term().count(); i++){
			Term baseTerm = api.term().findById(i);
			if(actTerm.equals(baseTerm.getValue())){
				addUsing(baseTerm);
			}
		}
	}

	void addUsing(Term term){
		Frequency newFreq = new Frequency(term.getYear(), term.getCount());
		if (!exist(newFreq))
			usingForYears.add(new Frequency(term.getYear(), term.getCount()));
	}

	boolean exist(Frequency freq){
		for(int i = 0; i < usingForYears.size(); i++){
			if(usingForYears.get(i).getYear() == freq.getYear()){
				usingForYears.get(i).addCount(freq.getCount());
				return true;
			}
		}
		return false;
	}

	@Override
	public String toString(){
		return "The term '"  +
				actTerm +
				"' used in the: " +
				usingForYears +
				" years\n";
	}
}

//хотела получше название, но не придумала, поэтому не вынесла в отдельный файл
class Frequency
{
	private int pubYear;
	private int yearCount;

	Frequency(int year, int count){
		pubYear = year;
		yearCount = count;
	}

	void addCount(int add){ yearCount+=add; }

	int getCount(){ return yearCount; }

	int getYear(){ return pubYear; }

	boolean equals(Term term){
		if (pubYear == term.getYear())
			return true;
		else
			return false;
	}

	@Override
	public String toString(){
		return pubYear + ": " + yearCount + " ";
	}
}
