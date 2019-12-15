package com.tencent.common.manifest.annotation;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.CLASS)
@Target({java.lang.annotation.ElementType.TYPE})
public @interface ExtensionImpl
{
  CreateMethod createMethod();
  
  Class<?> extension();
  
  String[] filters() default {};
  
  String[] hint() default {};
  
  Class<?>[] more() default {};
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\manifest\annotation\ExtensionImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */