package com.esc.datacollector.medline;

/**
 * Date: 4/18/2015
 * Time: 8:00 PM
 *
 * @author xanderblinov
 */
public class ObjectInfo
{
	public String value;

	public static ObjectInfo construct(MedlineObject object)
	{
		final ObjectInfo objectInfo = new ObjectInfo();

		objectInfo.value = object.value();

		return objectInfo;
	}
}
