package online.attrest.core.codetype;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Documented;

@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AttDescription 
{
    String value() default " No Description ";
}