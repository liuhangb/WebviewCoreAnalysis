package com.tencent.common.manifest;

import com.tencent.common.manifest.annotation.CreateMethod;

public class Implementation
{
  private final CreateMethod jdField_a_of_type_ComTencentCommonManifestAnnotationCreateMethod;
  private Class<?> jdField_a_of_type_JavaLangClass = null;
  private Object jdField_a_of_type_JavaLangObject = null;
  final String jdField_a_of_type_JavaLangString;
  private String[] jdField_a_of_type_ArrayOfJavaLangString = new String[0];
  private final String b;
  public final String[] hint;
  
  public Implementation(String paramString1, CreateMethod paramCreateMethod, String paramString2)
  {
    this(paramString1, paramCreateMethod, paramString2, null, null);
  }
  
  public Implementation(String paramString1, CreateMethod paramCreateMethod, String paramString2, String[] paramArrayOfString1, String[] paramArrayOfString2)
  {
    this.jdField_a_of_type_JavaLangString = paramString1;
    this.jdField_a_of_type_ComTencentCommonManifestAnnotationCreateMethod = paramCreateMethod;
    this.b = paramString2;
    if (paramArrayOfString1 != null) {
      this.jdField_a_of_type_ArrayOfJavaLangString = paramArrayOfString1;
    }
    paramString1 = paramArrayOfString2;
    if (paramArrayOfString2 == null) {
      paramString1 = new String[0];
    }
    this.hint = paramString1;
  }
  
  public Object query()
  {
    return query(null);
  }
  
  public Object query(Object paramObject)
  {
    if (paramObject != null)
    {
      paramObject = paramObject.toString();
      if (!((String)paramObject).isEmpty())
      {
        String[] arrayOfString = this.jdField_a_of_type_ArrayOfJavaLangString;
        if (arrayOfString.length != 0)
        {
          int m = arrayOfString.length;
          int k = 0;
          int i = 0;
          int j;
          for (;;)
          {
            j = k;
            if (i >= m) {
              break;
            }
            if (((String)paramObject).matches(arrayOfString[i]))
            {
              j = 1;
              break;
            }
            i += 1;
          }
          if (j != 0) {}
        }
        else
        {
          return null;
        }
      }
    }
    if (this.jdField_a_of_type_JavaLangClass == null) {
      try
      {
        paramObject = this.jdField_a_of_type_JavaLangClass;
        if (paramObject == null) {
          try
          {
            this.jdField_a_of_type_JavaLangClass = Class.forName(this.b);
          }
          catch (ClassNotFoundException paramObject)
          {
            ((ClassNotFoundException)paramObject).printStackTrace();
            throw new RuntimeException(toString(), (Throwable)paramObject);
          }
        }
      }
      finally {}
    }
    if (this.jdField_a_of_type_JavaLangObject == null) {
      try
      {
        if (this.jdField_a_of_type_JavaLangObject == null) {
          this.jdField_a_of_type_JavaLangObject = this.jdField_a_of_type_ComTencentCommonManifestAnnotationCreateMethod.invoke(this.jdField_a_of_type_JavaLangClass);
        }
      }
      finally {}
    }
    return this.jdField_a_of_type_JavaLangObject;
  }
  
  public String toString()
  {
    return String.format("Implementation{ interface = %s, createMethod = %s, class = %s }", new Object[] { this.jdField_a_of_type_JavaLangString, this.jdField_a_of_type_ComTencentCommonManifestAnnotationCreateMethod.name(), this.b });
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\manifest\Implementation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */