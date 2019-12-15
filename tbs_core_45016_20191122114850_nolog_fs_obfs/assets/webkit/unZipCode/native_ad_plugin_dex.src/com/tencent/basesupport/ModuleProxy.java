package com.tencent.basesupport;

import com.tencent.common.manifest.AppManifest;
import com.tencent.mtt.proguard.KeepNameAndPublic;

@KeepNameAndPublic
public class ModuleProxy<T>
{
  private Class<? extends T> jdField_a_of_type_JavaLangClass;
  private T jdField_a_of_type_JavaLangObject = null;
  private T b = null;
  
  private ModuleProxy(Class<? extends T> paramClass, T paramT)
  {
    this.jdField_a_of_type_JavaLangClass = paramClass;
    this.jdField_a_of_type_JavaLangObject = paramT;
  }
  
  public static <T> ModuleProxy<T> newProxy(Class<T> paramClass)
  {
    return newProxy(paramClass, null);
  }
  
  public static <T> ModuleProxy<T> newProxy(Class<T> paramClass, T paramT)
  {
    return new ModuleProxy(paramClass, paramT);
  }
  
  public T get()
  {
    Object localObject = this.b;
    if (localObject != null) {
      return (T)localObject;
    }
    localObject = AppManifest.getInstance().queryService(this.jdField_a_of_type_JavaLangClass);
    if (localObject != null) {
      return (T)localObject;
    }
    return (T)this.jdField_a_of_type_JavaLangObject;
  }
  
  public void set(T paramT)
  {
    this.b = paramT;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\basesupport\ModuleProxy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */