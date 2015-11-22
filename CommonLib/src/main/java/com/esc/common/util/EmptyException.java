package com.esc.common.util;

/**
 * Date: 13-Sep-15
 * Time: 9:57 PM
 *
 * @author xanderblinov
 */
public class EmptyException extends RuntimeException
{
	static final long serialVersionUID = 1;

	public EmptyException()
	{
	}

	public EmptyException(String message)
	{
		super(message);
	}

	public EmptyException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public EmptyException(Throwable cause)
	{
		super(cause);
	}

	public EmptyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
	{
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
