package lv.wings.dto;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface DTOMeta {
	/**
	 * Specify the target field of the class to gather propery value
	 */
	public String sourceField() default "";
	/**
	 * Specify whether this field shoud be processed
	 */
	public boolean ignore() default false;
}
