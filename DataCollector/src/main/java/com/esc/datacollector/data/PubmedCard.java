package com.esc.datacollector.data;

import com.esc.common.TextUtils;
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

	@MedlineField(value = OT, multiple = true)
	private String[] mKeyOt;

	@MedlineField(value = MH, multiple = true)
	private String[] mKeyMh;

	@MedlineField(value = DP)
	private String mDP;

	@MedlineField(value = DA)
	private String mDA;

	@MedlineField(TI)
	private String mTitle;

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

	public String[] getOrganizations()
	{
		return mAD;
	}

	public String getDP()
	{
		return mDP;
	}

	public String[] getKeyOt()
	{
		return mKeyOt;
	}

	public String[] getKeyMh() {	return mKeyMh; }


	public int getYear()
	{
		if (TextUtils.isEmpty(mDA) || mDA.length() < 4)
		{
			return 0;
		}
		return Integer.parseInt(mDA.substring(0, 4));
	}

	public String getTitle()
	{
		return mTitle;
	}
}
