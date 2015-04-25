package com.esc.datacollector.data;

import com.esc.datacollector.medline.MedlineField;
import com.esc.datacollector.medline.MedlineObject;

/**
 * Date: 4/19/2015
 * Time: 3:11 PM
 *
 * @author xanderblinov
 */
@MedlineObject
public class PubmedCard extends AbsPubmedCard
{
	@MedlineField(PMID)
	private String mPmid;

	@MedlineField(value = AU, multiple = true)
	private String[] mAU;

	@MedlineField(value = FAU, multiple = true)
	private String[] mFAU;

	@MedlineField(value = AD, multiple = true)
	private String[] mAD;

	@MedlineField(value = DP)
	private String mDP;

	public String getPmid()
	{
		return mPmid;
	}

	public String[] getAU()
	{
		return mAU;
	}

	public String[] getFAU()
	{
		return mFAU;
	}

	public String[] getAD()
	{
		return mAD;
	}

	public String getDP()
	{
		return mDP;
	}
}
