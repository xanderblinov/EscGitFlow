package com.esc.datacollector.medline;

import java.lang.reflect.Field;
import java.util.concurrent.locks.ReentrantLock;

import com.esc.common.TextUtils;

/**
 * Date: 4/19/2015
 * Time: 1:55 PM
 *
 * @author xanderblinov
 */
abstract class MedlineProcessor
{
	final ReentrantLock mLock = new ReentrantLock();

	FieldInfo mInfo;
	Field mField;
	Object mTarget;
	MedlineSource mMedlineSource;
	String mKey;
	ObjectInfo mObjectInfo;

	Boolean mArray;

	abstract void read();

	abstract void write();

	void readSource()
	{
		bindKeyName();

		read();
	}



	private void bindKeyName()  //true - read, false - write
	{

		if (TextUtils.isEmpty(mInfo.keyName))
		{
			throw new IllegalArgumentException("No keyname specified for field: " + getField());
		}

		if ((mObjectInfo == null || TextUtils.isEmpty(mObjectInfo.value)))
		{
			mKey = mInfo.keyName;
		}
	}



	boolean isArray()
	{
		if (mArray == null)
		{
			return mInfo.multiple;
		}

		return mArray;
	}



	String getField()
	{
		return mTarget.getClass().getSimpleName() + "#" + mField.getName();
	}
}
