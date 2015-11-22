package com.esc.datacollector.medline;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;


/**
 * Date: 4/18/2015
 * Time: 7:54 PM
 *
 * @author xanderblinov
 */
class MedlineCache
{
	private ConcurrentHashMap<Class<?>, ClassHolder> sClasses = new ConcurrentHashMap<Class<?>, ClassHolder>();

	private Object sLock = new Object();

	public ClassHolder readClass(Class<?> clazz)
	{
		ClassHolder classHolder = sClasses.get(clazz);

		if (classHolder != null)
		{
			return classHolder;
		}

		synchronized (sLock)
		{
			classHolder = sClasses.get(clazz);

			if (classHolder != null)
			{
				return classHolder;
			}

			final MedlineObject objAnno = clazz.getAnnotation(MedlineObject.class);

			final Field[] fields = clazz.getDeclaredFields();
			final ArrayList<FieldHolder> holder = new ArrayList<FieldHolder>();

			for (final Field field : fields)
			{
				field.setAccessible(true);

				final int modifiers = field.getModifiers();

				if (field.isSynthetic() || Modifier.isTransient(modifiers) || Modifier.isStatic(modifiers))
				{
					continue;
				}

				final MedlineField annotation = field.getAnnotation(MedlineField.class);

				if (annotation == null)
				{
					continue;
				}

				if (Modifier.isFinal(modifiers))
				{
					throw new IllegalStateException("Field cannot be final: " + clazz.getSimpleName() + "#" + field.getName());
				}

				FieldInfo info;

				if (annotation == null)
				{
					info = FieldInfo.construct(field);
				}
				else
				{
					info = FieldInfo.construct(annotation, field);
				}

				final FieldHolder fieldHolder = new FieldHolder(field, info);
				holder.add(fieldHolder);
			}

			ObjectInfo info = null;

			if (objAnno != null)
			{
				info = ObjectInfo.construct(objAnno);
			}

			classHolder = new ClassHolder(holder, info);

			sClasses.put(clazz, classHolder);

			return classHolder;
		}
	}

	public static class ClassHolder
	{
		public final ArrayList<FieldHolder> fields;
		public final ObjectInfo info;

		public ClassHolder(ArrayList<FieldHolder> fields, ObjectInfo info)
		{
			this.fields = fields;
			this.info = info;
		}
	}

	public static class FieldHolder
	{
		public final Field field;
		public final FieldInfo info;

		public FieldHolder(Field field, FieldInfo info)
		{
			this.field = field;
			this.info = info;
		}
	}
}
