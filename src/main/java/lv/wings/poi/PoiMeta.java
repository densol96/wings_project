package lv.wings.poi;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
//Klase, lai definetu lauka iestatījumus, piemēram virsrakstu vai izejošo datu formātu
public @interface PoiMeta {
	public String name() default "";
	public String valueFormat() default "{}";
}