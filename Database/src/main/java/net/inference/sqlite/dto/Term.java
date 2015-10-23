package net.inference.sqlite.dto;

import java.util.ArrayList;
import java.util.List;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import net.inference.database.dto.ITerm;

/**
 * Date: 12/19/2014
 * Time: 3:15 PM
 *
 * @author xanderblinov
 */

@DatabaseTable(tableName = ITerm.TABLE_NAME)
public class Term implements ITerm
{
	@DatabaseField(columnName = Column.value, id = true)
	private String mValue;
	@DatabaseField(columnName = Column.publication)
	private String mPublication;
	@DatabaseField(columnName = Column.neighbors)
	private String mNeighbors;

	public Term()
	{
		// ORMLite needs a no-arg constructor
	}


	public Term(String value, String publication, String [] neighbors)
	{
		mValue = value.toLowerCase().trim();
		mPublication = publication;
		String neighbors_to_str = "-";
		if(neighbors.length > 0){
			neighbors_to_str = neighbors[0].trim().toLowerCase();
			for(int i = 1; i<neighbors.length;i++)
				neighbors_to_str.concat(","+neighbors[i].trim().toLowerCase());
		}
		mNeighbors = neighbors_to_str;
	}

	public String getValue()
	{
		return mValue;
	}

	public void setValue(final String value)
	{
		mValue = value;
	}

	public String getPublication()	{ return	mPublication;}

	public void setPublication(final String publication)
	{
		mPublication = publication;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof Term))
		{
			return false;
		}

		Term term = (Term) o;

		return !(getValue() != null ? !getValue().equals(term.getValue()) : term.getValue() != null);

	}

	@Override
	public int hashCode()
	{
		return getValue() != null ? getValue().hashCode() : 0;
	}

	@Override
	public String toString()
	{
		return "Value is: " + mValue + ", publication: " + mPublication;
	}

	@Override
	public ArrayList<String> setEncoding()
	{
		ArrayList<String> createdTerms = new ArrayList<>();
		String termStr = this.getValue();
		String [] termArr = termStr.split(",");
		for (int i = 0; i < termArr.length; i++)
		{
			String newTerm = new String(termArr[i]);
			createdTerms.add(newTerm);
		}
		return createdTerms;
	}

	public String getNeighbors() { return mNeighbors; }

	public void addNeighbors(final String [] neighbors){
		for(int i = 0; i < neighbors.length; i++){
			if(!existNeighbor(neighbors[i]))
				mNeighbors.concat("," + neighbors[i].trim().toLowerCase()) ;
		}
	}

	public boolean existNeighbor(final String neighbor)
	{
		String [] neiArr = neighbor.split(",");
		for(int i = 0; i < neiArr.length; i++){
			if(neiArr[i] == neighbor)
				return true;
		}
		return false;
	}

}
