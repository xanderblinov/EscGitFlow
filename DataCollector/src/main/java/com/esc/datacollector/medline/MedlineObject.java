package com.esc.datacollector.medline;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Date: 4/18/2015
 * Time: 7:58 PM
 *
 * @author xanderblinov
 */
@Target(TYPE)
@Retention(RUNTIME)
public @interface MedlineObject
{
	String value() default "";
}
