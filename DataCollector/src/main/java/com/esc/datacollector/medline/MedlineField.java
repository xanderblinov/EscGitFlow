package com.esc.datacollector.medline;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Date: 4/18/2015
 * Time: 7:44 PM
 *
 * @author xanderblinov
 */
@Target(FIELD)
@Retention(RUNTIME)
public @interface MedlineField
{
	/**
	 * Name of the key of MEDLINE entry for field.
	 */
	String value() default "";

	/**
	 * Allows multiple field entry in card
	 */
	boolean multiple() default false;

}
