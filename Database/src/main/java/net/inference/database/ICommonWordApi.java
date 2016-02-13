package net.inference.database;

import java.util.List;

import net.inference.database.dto.ICommonWord;
import net.inference.sqlite.dto.PrimitiveTerm;
import net.inference.sqlite.dto.CommonWord;


public interface ICommonWordApi extends IBaseApi <CommonWord,Integer>
{
	List<CommonWord> addCommonWords(List<CommonWord> commonWords) throws Exception;

	default CommonWord addCommonWord(CommonWord commonWord)
	{
		return null;
	}
}
