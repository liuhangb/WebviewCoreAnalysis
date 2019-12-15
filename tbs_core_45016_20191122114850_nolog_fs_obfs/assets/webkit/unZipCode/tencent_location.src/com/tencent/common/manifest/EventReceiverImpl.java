package com.tencent.common.manifest;

import com.tencent.common.manifest.annotation.CreateMethod;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Iterator;

public class EventReceiverImpl
{
  final int jdField_a_of_type_Int;
  private final CreateMethod jdField_a_of_type_ComTencentCommonManifestAnnotationCreateMethod;
  private Class<?> jdField_a_of_type_JavaLangClass = null;
  private Object jdField_a_of_type_JavaLangObject = null;
  final String jdField_a_of_type_JavaLangString;
  private Method jdField_a_of_type_JavaLangReflectMethod = null;
  private HashSet<Object> jdField_a_of_type_JavaUtilHashSet = new HashSet();
  final String b;
  private final String c;
  private final String d;
  
  public EventReceiverImpl(String paramString1, String paramString2, CreateMethod paramCreateMethod, int paramInt, String paramString3, String paramString4)
  {
    this.jdField_a_of_type_JavaLangString = paramString1;
    this.c = paramString2;
    this.jdField_a_of_type_ComTencentCommonManifestAnnotationCreateMethod = paramCreateMethod;
    this.jdField_a_of_type_Int = paramInt;
    this.d = paramString3;
    this.b = paramString4;
  }
  
  void a()
  {
    if (this.jdField_a_of_type_JavaLangClass == null) {
      try
      {
        Class localClass = this.jdField_a_of_type_JavaLangClass;
        if (localClass == null) {
          try
          {
            this.jdField_a_of_type_JavaLangClass = Class.forName(this.c);
          }
          catch (ClassNotFoundException localClassNotFoundException)
          {
            localClassNotFoundException.printStackTrace();
            throw new RuntimeException(toString(), localClassNotFoundException);
          }
        }
        return;
      }
      finally {}
    }
  }
  
  public void invoke(EventMessage paramEventMessage)
  {
    Object localObject1 = new HashSet();
    synchronized (this.jdField_a_of_type_JavaUtilHashSet)
    {
      if (!this.jdField_a_of_type_JavaUtilHashSet.isEmpty()) {
        ((HashSet)localObject1).addAll(this.jdField_a_of_type_JavaUtilHashSet);
      }
      if ((CreateMethod.NONE == this.jdField_a_of_type_ComTencentCommonManifestAnnotationCreateMethod) && (((HashSet)localObject1).isEmpty())) {
        return;
      }
      a();
      if (this.jdField_a_of_type_JavaLangReflectMethod == null) {
        try
        {
          ??? = this.jdField_a_of_type_JavaLangReflectMethod;
          if (??? == null) {
            try
            {
              this.jdField_a_of_type_JavaLangReflectMethod = this.jdField_a_of_type_JavaLangClass.getDeclaredMethod(this.d, new Class[] { EventMessage.class });
              this.jdField_a_of_type_JavaLangReflectMethod.setAccessible(true);
            }
            catch (NoSuchMethodException paramEventMessage)
            {
              paramEventMessage.printStackTrace();
              throw new RuntimeException(toString(), paramEventMessage);
            }
          }
        }
        finally {}
      }
      if ((this.jdField_a_of_type_JavaLangObject == null) && (this.jdField_a_of_type_ComTencentCommonManifestAnnotationCreateMethod != CreateMethod.NONE)) {
        try
        {
          if ((this.jdField_a_of_type_JavaLangObject == null) && (this.jdField_a_of_type_ComTencentCommonManifestAnnotationCreateMethod != CreateMethod.NONE)) {
            this.jdField_a_of_type_JavaLangObject = this.jdField_a_of_type_ComTencentCommonManifestAnnotationCreateMethod.invoke(this.jdField_a_of_type_JavaLangClass);
          }
        }
        finally {}
      }
      ??? = this.jdField_a_of_type_JavaLangObject;
      if (??? != null) {
        ((HashSet)localObject1).add(???);
      }
      try
      {
        localObject1 = ((HashSet)localObject1).iterator();
        while (((Iterator)localObject1).hasNext())
        {
          ??? = ((Iterator)localObject1).next();
          this.jdField_a_of_type_JavaLangReflectMethod.invoke(???, new Object[] { paramEventMessage });
        }
        return;
      }
      catch (InvocationTargetException paramEventMessage)
      {
        paramEventMessage.printStackTrace();
        throw new RuntimeException(toString(), paramEventMessage);
      }
      catch (IllegalAccessException paramEventMessage)
      {
        paramEventMessage.printStackTrace();
        throw new RuntimeException(toString(), paramEventMessage);
      }
    }
  }
  
  public boolean register(Object paramObject)
  {
    a();
    if (this.jdField_a_of_type_JavaLangClass.isInstance(paramObject)) {
      synchronized (this.jdField_a_of_type_JavaUtilHashSet)
      {
        this.jdField_a_of_type_JavaUtilHashSet.add(paramObject);
        return true;
      }
    }
    return false;
  }
  
  public String toString()
  {
    return String.format("EventReceiverImpl{ eventName = %s, createMethod = %s, className = %s, prior = %d, thread = %s", new Object[] { this.jdField_a_of_type_JavaLangString, this.jdField_a_of_type_ComTencentCommonManifestAnnotationCreateMethod.name(), this.c, Integer.valueOf(this.jdField_a_of_type_Int), this.b });
  }
  
  public boolean unregister(Object paramObject)
  {
    if (paramObject.getClass().getName().equals(this.c)) {
      synchronized (this.jdField_a_of_type_JavaUtilHashSet)
      {
        this.jdField_a_of_type_JavaUtilHashSet.remove(paramObject);
        return true;
      }
    }
    return false;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\manifest\EventReceiverImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */