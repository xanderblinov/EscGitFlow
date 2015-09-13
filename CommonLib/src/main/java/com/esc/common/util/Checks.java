package com.esc.common.util;

import com.esc.common.TextUtils;

import java.util.Collection;
import java.util.Map;

/**
 * Date: 13-Sep-15
 * Time: 9:54 PM
 *
 * @author xanderblinov
 */
public class Checks
{
	public static <T> T requireInstanceOf(Object obj, Class<T> clazz)
	{
		return requireInstanceOf(obj, clazz, null);
	}

	public static <T> T requireInstanceOf(Object obj, Class<T> clazz, String message)
	{
		if (clazz.isInstance(requireNotNull(obj, null)))
		{
			return clazz.cast(obj);
		}

		throw new ClassCastException(message);
	}

	public static <T> T requireNotNull(T object)
	{
		return requireNotNull(object, null);
	}

	public static <T> T requireNotNull(T object, String message)
	{
		if (object == null)
		{
			throw new NullPointerException(message);
		}

		return object;
	}

	public static <T extends Collection<?>> T requireNotEmpty(T collectoion)
	{
		return requireNotEmpty(collectoion, null);
	}

	public static <T extends Collection<?>> T requireNotEmpty(T collectoion, String message)
	{
		if (requireNotNull(collectoion, message).isEmpty())
		{
			throw new EmptyException(message);
		}

		return collectoion;
	}

	public static <T extends Map<?, ?>> T requireNotEmpty(T map)
	{
		return requireNotEmpty(map, null);
	}

	public static <T extends Map<?, ?>> T requireNotEmpty(T map, String message)
	{
		if (requireNotNull(map, message).isEmpty())
		{
			throw new EmptyException(message);
		}

		return map;
	}

	public static <T extends CharSequence> T requireNotEmpty(T string)
	{
		return requireNotEmpty(string, null);
	}

	public static <T extends CharSequence> T requireNotEmpty(T string, String message)
	{
		if (requireNotNull(string, message).length() == 0)
		{
			throw new EmptyException(message);
		}

		return string;
	}

	public static <T> T[] requireNotEmpty(T[] array)
	{
		return requireNotEmpty(array, null);
	}

	public static <T> T[] requireNotEmpty(T[] array, String message)
	{
		if (requireNotNull(array, message).length == 0)
		{
			throw new EmptyException(message);
		}

		return array;
	}

	public static byte[] requireNotEmpty(byte[] array)
	{
		return requireNotEmpty(array, null);
	}

	public static byte[] requireNotEmpty(byte[] array, String message)
	{
		if (requireNotNull(array, message).length == 0)
		{
			throw new EmptyException(message);
		}

		return array;
	}

	public static short[] requireNotEmpty(short[] array)
	{
		return requireNotEmpty(array, null);
	}

	public static short[] requireNotEmpty(short[] array, String message)
	{
		if (requireNotNull(array, message).length == 0)
		{
			throw new EmptyException(message);
		}

		return array;
	}

	public static char[] requireNotEmpty(char[] array)
	{
		return requireNotEmpty(array, null);
	}

	public static char[] requireNotEmpty(char[] array, String message)
	{
		if (requireNotNull(array, message).length == 0)
		{
			throw new EmptyException(message);
		}

		return array;
	}

	public static int[] requireNotEmpty(int[] array)
	{
		return requireNotEmpty(array, null);
	}

	public static int[] requireNotEmpty(int[] array, String message)
	{
		if (requireNotNull(array, message).length == 0)
		{
			throw new EmptyException(message);
		}

		return array;
	}

	public static long[] requireNotEmpty(long[] array)
	{
		return requireNotEmpty(array, null);
	}

	public static long[] requireNotEmpty(long[] array, String message)
	{
		if (requireNotNull(array, message).length == 0)
		{
			throw new EmptyException(message);
		}

		return array;
	}

	public static float[] requireNotEmpty(float[] array)
	{
		return requireNotEmpty(array, null);
	}

	public static float[] requireNotEmpty(float[] array, String message)
	{
		if (requireNotNull(array, message).length == 0)
		{
			throw new EmptyException(message);
		}

		return array;
	}

	public static double[] requireNotEmpty(double[] array)
	{
		return requireNotEmpty(array, null);
	}

	public static double[] requireNotEmpty(double[] array, String message)
	{
		if (requireNotNull(array, message).length == 0)
		{
			throw new EmptyException(message);
		}

		return array;
	}


	public static <T> T as(Object obj, Class<T> clazz)
	{
		if (clazz.isInstance(obj))
		{
			return clazz.cast(obj);
		}

		return null;
	}

	public static boolean isEmpty(CharSequence string)
	{
		return TextUtils.isEmpty(string);
	}



	public static <T> boolean isEmpty(T[] array)
	{
		if (array == null || array.length == 0)
		{
			return true;
		}

		return false;
	}

	public static boolean isEmpty(int[] array)
	{
		if (array == null || array.length == 0)
		{
			return true;
		}

		return false;
	}

	public static boolean isEmpty(long[] array)
	{
		if (array == null || array.length == 0)
		{
			return true;
		}

		return false;
	}

	public static boolean isEmpty(byte[] array)
	{
		if (array == null || array.length == 0)
		{
			return true;
		}

		return false;
	}

	public static boolean isEmpty(short[] array)
	{
		if (array == null || array.length == 0)
		{
			return true;
		}

		return false;
	}

	public static boolean isEmpty(char[] array)
	{
		if (array == null || array.length == 0)
		{
			return true;
		}

		return false;
	}

	public static boolean isEmpty(float[] array)
	{
		if (array == null || array.length == 0)
		{
			return true;
		}

		return false;
	}

	public static boolean isEmpty(double[] array)
	{
		if (array == null || array.length == 0)
		{
			return true;
		}

		return false;
	}

	public static boolean isEmpty(boolean[] array)
	{
		if (array == null || array.length == 0)
		{
			return true;
		}

		return false;
	}

	public static boolean isEmpty(Map<?, ?> map)
	{
		if (map == null || map.isEmpty())
		{
			return true;
		}

		return false;
	}

	public static boolean isEmpty(Collection<?> collection)
	{
		if (collection == null || collection.isEmpty())
		{
			return true;
		}

		return false;
	}

	private Checks()
	{
	}
}
