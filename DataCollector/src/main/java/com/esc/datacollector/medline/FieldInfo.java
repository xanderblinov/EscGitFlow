package com.esc.datacollector.medline;


import java.lang.reflect.Field;

/**
 * Date: 4/18/2015
 * Time: 8:03 PM
 *
 * @author xanderblinov
 */
public class FieldInfo
{
	public String keyName;
	public boolean multiple;

	public static FieldInfo construct(Field field)
	{
		final FieldInfo info = new FieldInfo();

		info.keyName = field.getName();
		info.multiple = false;

		return info;
	}

	public static FieldInfo construct(MedlineField anno, Field field)
	{
		final FieldInfo info = new FieldInfo();

		info.keyName = anno.value();
		info.multiple = anno.multiple();

		return info;
	}


}
