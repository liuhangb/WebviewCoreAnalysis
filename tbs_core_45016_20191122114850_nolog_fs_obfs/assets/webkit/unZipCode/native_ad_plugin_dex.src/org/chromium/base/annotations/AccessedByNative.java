package org.chromium.base.annotations;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.CLASS)
@Target({java.lang.annotation.ElementType.FIELD})
public @interface AccessedByNative
{
  String value() default "";
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\base\annotations\AccessedByNative.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */