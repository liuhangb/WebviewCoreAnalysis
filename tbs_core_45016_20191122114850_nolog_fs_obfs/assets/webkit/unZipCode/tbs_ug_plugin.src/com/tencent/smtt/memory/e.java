package com.tencent.smtt.memory;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class e
{
  private static ConcurrentHashMap<String, e> jdField_a_of_type_JavaUtilConcurrentConcurrentHashMap = new ConcurrentHashMap();
  private static ConcurrentHashMap<String, e> jdField_b_of_type_JavaUtilConcurrentConcurrentHashMap = new ConcurrentHashMap();
  private int jdField_a_of_type_Int;
  private String jdField_a_of_type_JavaLangString;
  private int jdField_b_of_type_Int;
  private int c;
  private int d;
  
  private e(int paramInt1, String paramString, int paramInt2)
  {
    this.jdField_a_of_type_Int = paramInt1;
    this.jdField_b_of_type_Int = paramInt2;
    this.jdField_a_of_type_JavaLangString = paramString;
    this.c = 0;
    this.d = 5;
  }
  
  private static e a(String paramString)
  {
    e locale2 = (e)jdField_a_of_type_JavaUtilConcurrentConcurrentHashMap.get(paramString);
    e locale1 = locale2;
    if (locale2 == null) {
      locale1 = (e)jdField_b_of_type_JavaUtilConcurrentConcurrentHashMap.get(paramString);
    }
    return locale1;
  }
  
  public static void a(int paramInt1, String paramString, int paramInt2)
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("report: id = ");
    ((StringBuilder)localObject).append(paramInt1);
    ((StringBuilder)localObject).append(", url = ");
    ((StringBuilder)localObject).append(paramString);
    ((StringBuilder)localObject).append(", status = ");
    ((StringBuilder)localObject).append(paramInt2);
    c.a("webstatus", ((StringBuilder)localObject).toString());
    e locale = a(Integer.toString(paramInt1));
    localObject = locale;
    if (locale == null)
    {
      localObject = new e(paramInt1, paramString, paramInt2);
      if (((e)localObject).d == 5) {
        jdField_b_of_type_JavaUtilConcurrentConcurrentHashMap.put(Integer.toString(paramInt1), localObject);
      } else {
        jdField_a_of_type_JavaUtilConcurrentConcurrentHashMap.put(Integer.toString(paramInt1), localObject);
      }
    }
    if (paramString == null) {
      paramString = "";
    }
    ((e)localObject).jdField_a_of_type_JavaLangString = paramString;
    ((e)localObject).jdField_b_of_type_Int = paramInt2;
    switch (paramInt2)
    {
    default: 
      return;
    case 5: 
      ((e)localObject).d = paramInt2;
      jdField_a_of_type_JavaUtilConcurrentConcurrentHashMap.remove(Integer.toString(paramInt1));
      jdField_b_of_type_JavaUtilConcurrentConcurrentHashMap.remove(Integer.toString(paramInt1));
      jdField_b_of_type_JavaUtilConcurrentConcurrentHashMap.put(Integer.toString(paramInt1), localObject);
      break;
    case 4: 
      ((e)localObject).d = paramInt2;
      jdField_b_of_type_JavaUtilConcurrentConcurrentHashMap.remove(Integer.toString(paramInt1));
      jdField_a_of_type_JavaUtilConcurrentConcurrentHashMap.remove(Integer.toString(paramInt1));
      jdField_a_of_type_JavaUtilConcurrentConcurrentHashMap.put(Integer.toString(paramInt1), localObject);
      break;
    case 3: 
      ((e)localObject).c = paramInt2;
      jdField_b_of_type_JavaUtilConcurrentConcurrentHashMap.remove(Integer.toString(paramInt1));
      jdField_a_of_type_JavaUtilConcurrentConcurrentHashMap.remove(Integer.toString(paramInt1));
      break;
    case 0: 
    case 1: 
    case 2: 
      ((e)localObject).c = paramInt2;
    }
    MemoryChecker.reportWebStatus((e)localObject);
  }
  
  public static String[] a()
  {
    String[] arrayOfString = new String[jdField_a_of_type_JavaUtilConcurrentConcurrentHashMap.size()];
    Iterator localIterator = jdField_a_of_type_JavaUtilConcurrentConcurrentHashMap.entrySet().iterator();
    int i = 0;
    while (localIterator.hasNext())
    {
      arrayOfString[i] = ((e)((Map.Entry)localIterator.next()).getValue()).a();
      i += 1;
    }
    return arrayOfString;
  }
  
  public static String b()
  {
    String[] arrayOfString = a();
    int j = arrayOfString.length;
    if (j <= 0) {
      return "";
    }
    String str = new String(arrayOfString[0]);
    int i = 1;
    while (i < j)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append(str);
      localStringBuilder.append(";");
      str = localStringBuilder.toString();
      localStringBuilder = new StringBuilder();
      localStringBuilder.append(str);
      localStringBuilder.append(arrayOfString[i]);
      str = localStringBuilder.toString();
      i += 1;
    }
    return str;
  }
  
  public int a()
  {
    return this.jdField_a_of_type_Int;
  }
  
  public String a()
  {
    return this.jdField_a_of_type_JavaLangString;
  }
  
  public int b()
  {
    return this.jdField_b_of_type_Int;
  }
  
  public int c()
  {
    return this.c;
  }
  
  public int d()
  {
    return this.d;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\memory\e.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */