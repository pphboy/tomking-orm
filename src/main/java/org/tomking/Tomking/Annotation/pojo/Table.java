package org.tomking.Tomking.Annotation.pojo;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Table {

	/**
	 * 表名
	 * @return
	 */
	String value() default "";
	/**
	 * 表名
	 * @return
	 */
	String name() default "";
	
	/**
	 * 引擎
	 * @return
	 */
	String engine() default "InnoDB";
}
