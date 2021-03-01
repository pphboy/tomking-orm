package org.tomking.Tomking.Annotation.pojo;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.tomking.Tomking.enums.FieldBind;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Column {
	/**
	 * 字段名
	 * @return
	 */
	String value() default "" ;
	
	FieldBind[] bind();
	
	/**
	 * 字段名
	 * @return
	 */
	String name() default "";

	/**
	 * 字段长度<br>
	 * 目前只能设置字符串长度
	 * @return
	 */
	int length() default 0;
}
