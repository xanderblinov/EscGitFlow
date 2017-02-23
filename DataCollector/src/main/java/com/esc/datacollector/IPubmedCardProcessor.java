package com.esc.datacollector;

import com.esc.datacollector.data.PubmedCard;

/**
 * Date: 13-Sep-15
 * Time: 9:50 PM
 *
 * @author xanderblinov
 */
public interface IPubmedCardProcessor
{
	/**
	 *
	 * @param pubmedCard essential of article
	 * @return true if processing success
	 */
	boolean execute(PubmedCard pubmedCard);
//	void addTerms();
//	void addTermsYear();
}
