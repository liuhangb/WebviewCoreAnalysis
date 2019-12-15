package org.chromium.base.annotations;

import java.lang.annotation.Annotation;
import java.lang.annotation.Target;

@Target({java.lang.annotation.ElementType.METHOD, java.lang.annotation.ElementType.FIELD, java.lang.annotation.ElementType.TYPE, java.lang.annotation.ElementType.CONSTRUCTOR})
public @interface UsedByReflection
{
  String value();
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\base\annotations\UsedByReflection.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */