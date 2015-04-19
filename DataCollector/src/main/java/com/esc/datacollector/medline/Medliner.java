package com.esc.datacollector.medline;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import com.esc.datacollector.medline.MedlineCache.ClassHolder;
import com.esc.datacollector.medline.MedlineCache.FieldHolder;
import javafx.util.Pair;

/**
 * Date: 4/18/2015
 * Time: 8:11 PM
 *
 * @author xanderblinov
 */
public class Medliner
{
	private static final String TAG = "Medliner";

	private static final MedlineCache sClassCache = new MedlineCache();


	/**
	 * Reads {@link MedlineSource} in instance of {@link Class} provided. Uses
	 * {@link MedlineField} annotation.
	 *
	 * @param obj   json to read
	 * @param clazz your class
	 * @param <T>   type of your class
	 * @return result instance
	 */
	public static <T> T readMedline(MedlineSource obj, Class<T> clazz)
	{
		if (obj == null)
		{
			throw new IllegalArgumentException("JSONObject cannot be null");
		}

		if (clazz == null)
		{
			throw new IllegalArgumentException("Class cannot be null");
		}

		T result = null;

		try
		{
			result = clazz.newInstance();
		}
		catch (final InstantiationException e)
		{
			throw new IllegalArgumentException("Could not instantiate target object", e);
		}
		catch (final IllegalAccessException e)
		{
			throw new IllegalArgumentException("Could not instantiate target object", e);
		}


		final ClassHolder holder = sClassCache.readClass(clazz);
		final List<FieldHolder> fields = holder.fields;


		for (final FieldHolder fieldHolder : fields)
		{
			read(obj, result, holder, fieldHolder);
		}


		return result;
	}

	private static <T> void read(MedlineSource obj, T result, final ClassHolder holder, final FieldHolder fieldHolder)
	{
		if (fieldHolder.info.multiple)
		{
			Class<?> arrayObjectClass = null;

			try
			{
				arrayObjectClass = fieldHolder.field.getType().getComponentType();
			}
			catch (final Exception e)
			{
				throw new IllegalArgumentException("Could not get item class for field: " + fieldHolder.field.getName(), e);
			}

			if (arrayObjectClass == null)
			{
				throw new IllegalArgumentException("Could not get item class for field: " + fieldHolder.field.getName());
			}


			ArrayList<Object> arrayList = new ArrayList<Object>();
			for (Pair<String, String> pair : obj.getFieldList())
			{
				if (fieldHolder.info.keyName.equals(pair.getKey()))
				{
					arrayList.add(pair.getValue());
				}
			}
			if (!arrayList.isEmpty())
			{
				final T[] items = (T[]) Array.newInstance(arrayObjectClass, arrayList.size());

				for (int i = 0; i < arrayList.size(); i++)
				{
					items[i] = (T) arrayList.get(i);
				}
				try
				{
					fieldHolder.field.setAccessible(true);
					fieldHolder.field.set(result, items);
				}
				catch (IllegalAccessException e)
				{
					e.printStackTrace();
				}

			}
		}
		else
		{
			for (Pair<String, String> pair : obj.getFieldList())
			{
				if (readSingleField(fieldHolder, pair, result))
				{
					break;
				}
			}
		}

	}


	private static <T> boolean readSingleField(final FieldHolder fieldHolder, final Pair<String, String> pair, final T result)
	{
		if (fieldHolder.info.keyName.equals(pair.getKey()))
		{
			try
			{
				fieldHolder.field.setAccessible(true);
				fieldHolder.field.set(result, pair.getValue());
			}
			catch (IllegalAccessException e)
			{
				e.printStackTrace();
			}
			return true;
		}
		return false;
	}


}
