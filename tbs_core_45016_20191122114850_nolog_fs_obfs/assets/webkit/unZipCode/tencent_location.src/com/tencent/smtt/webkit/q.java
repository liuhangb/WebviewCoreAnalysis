package com.tencent.smtt.webkit;

import android.util.Log;
import java.lang.reflect.Method;

public class q
{
  private static String jdField_a_of_type_JavaLangString = "com.tencent.smtt.utils.TbsLog";
  private static boolean jdField_a_of_type_Boolean = false;
  private static String[] jdField_a_of_type_ArrayOfJavaLangString;
  private static Method[] jdField_a_of_type_ArrayOfJavaLangReflectMethod = { null, null, null, null, null };
  
  static
  {
    jdField_a_of_type_ArrayOfJavaLangString = new String[] { "i", "d", "e", "v", "w" };
  }
  
  private static void a()
    throws NoSuchMethodException, ClassNotFoundException
  {
    try
    {
      jdField_a_of_type_Boolean = true;
      Class localClass = Class.forName(jdField_a_of_type_JavaLangString);
      int i = 0;
      while (i < jdField_a_of_type_ArrayOfJavaLangReflectMethod.length)
      {
        jdField_a_of_type_ArrayOfJavaLangReflectMethod[i] = localClass.getMethod(jdField_a_of_type_ArrayOfJavaLangString[i], new Class[] { String.class, String.class });
        jdField_a_of_type_ArrayOfJavaLangReflectMethod[i].setAccessible(true);
        i += 1;
      }
      return;
    }
    finally {}
  }
  
  public static void a(String paramString1, String paramString2)
  {
    a("e", paramString1, paramString2);
  }
  
  public static void a(String paramString1, String paramString2, String paramString3)
  {
    int i;
    if ("i".equals(paramString1))
    {
      i = 0;
    }
    else if ("d".equals(paramString1))
    {
      i = 1;
    }
    else if ("e".equals(paramString1))
    {
      i = 2;
    }
    else if ("v".equals(paramString1))
    {
      i = 3;
    }
    else
    {
      if (!"w".equals(paramString1)) {
        break label140;
      }
      i = 4;
    }
    try
    {
      if (!jdField_a_of_type_Boolean) {
        a();
      }
      paramString1 = jdField_a_of_type_ArrayOfJavaLangReflectMethod[i];
      if (paramString1 != null)
      {
        paramString1.invoke(null, new Object[] { paramString2, paramString3 });
        return;
      }
    }
    catch (Throwable paramString1)
    {
      paramString3 = new StringBuilder();
      paramString3.append("tbslog exceptions:");
      paramString3.append(Log.getStackTraceString(paramString1));
      Log.e(paramString2, paramString3.toString());
    }
    return;
    label140:
    paramString3 = new StringBuilder();
    paramString3.append("tbslog exceptions - invalid level:");
    paramString3.append(paramString1);
    Log.e(paramString2, paramString3.toString());
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\webkit\q.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */