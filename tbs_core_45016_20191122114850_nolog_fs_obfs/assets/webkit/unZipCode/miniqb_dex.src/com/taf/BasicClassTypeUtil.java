package com.taf;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class BasicClassTypeUtil
{
  private static ClassLoader jdField_a_of_type_JavaLangClassLoader;
  private static boolean jdField_a_of_type_Boolean = true;
  
  private static void a(ArrayList<String> paramArrayList, String paramString)
  {
    int i = paramString.length();
    int j;
    do
    {
      j = i;
      if (paramString.charAt(i - 1) != '>') {
        break;
      }
      j = i - 1;
      i = j;
    } while (j != 0);
    paramArrayList.add(0, uni2JavaType(paramString.substring(0, j)));
  }
  
  public static Object createClassByName(String paramString, boolean paramBoolean, ClassLoader paramClassLoader)
    throws ObjectCreateException
  {
    if (paramString.equals("java.lang.Integer")) {
      return Integer.valueOf(0);
    }
    if (paramString.equals("java.lang.Boolean")) {
      return Boolean.valueOf(false);
    }
    if (paramString.equals("java.lang.Byte")) {
      return Byte.valueOf((byte)0);
    }
    if (paramString.equals("java.lang.Double")) {
      return Double.valueOf(0.0D);
    }
    if (paramString.equals("java.lang.Float")) {
      return Float.valueOf(0.0F);
    }
    if (paramString.equals("java.lang.Long")) {
      return Long.valueOf(0L);
    }
    if (paramString.equals("java.lang.Short")) {
      return Short.valueOf((short)0);
    }
    if (!paramString.equals("java.lang.Character"))
    {
      if (paramString.equals("java.lang.String")) {
        return "";
      }
      if (paramString.equals("java.util.List")) {
        return new ArrayList();
      }
      if (paramString.equals("java.util.Map")) {
        return new HashMap();
      }
      if (paramString.equals("Array")) {
        return "Array";
      }
      if (paramString.equals("?")) {
        return paramString;
      }
      if (paramClassLoader != null) {}
      try
      {
        paramString = Class.forName(paramString, paramBoolean, paramClassLoader);
        break label215;
        if (jdField_a_of_type_JavaLangClassLoader != null) {
          paramString = Class.forName(paramString, jdField_a_of_type_Boolean, jdField_a_of_type_JavaLangClassLoader);
        } else {
          paramString = Class.forName(paramString);
        }
        label215:
        paramString = paramString.getConstructor(new Class[0]).newInstance(new Object[0]);
        return paramString;
      }
      catch (Exception paramString)
      {
        paramString.printStackTrace();
        throw new ObjectCreateException(paramString);
      }
    }
    throw new IllegalArgumentException("can not support java.lang.Character");
  }
  
  public static Object createClassByUni(String paramString, boolean paramBoolean, ClassLoader paramClassLoader)
    throws ObjectCreateException
  {
    Iterator localIterator = getTypeList(paramString).iterator();
    Object localObject = null;
    paramString = (String)localObject;
    String str1 = paramString;
    while (localIterator.hasNext())
    {
      localObject = createClassByName((String)localIterator.next(), paramBoolean, paramClassLoader);
      boolean bool = localObject instanceof String;
      int j = 0;
      if (bool)
      {
        String str2 = (String)localObject;
        if ("Array".equals(str2))
        {
          if (paramString != null) {
            continue;
          }
          localObject = Array.newInstance(Byte.class, 0);
          continue;
        }
        if ("?".equals(str2)) {
          continue;
        }
        if (paramString != null) {
          break label233;
        }
      }
      else
      {
        if ((localObject instanceof List))
        {
          if ((paramString != null) && ((paramString instanceof Byte)))
          {
            localObject = Array.newInstance(Byte.class, 1);
            Array.set(localObject, 0, paramString);
            continue;
          }
          if (paramString != null) {
            ((List)localObject).add(paramString);
          }
          paramString = null;
          continue;
        }
        if ((localObject instanceof Map))
        {
          int i;
          if (paramString != null) {
            i = 1;
          } else {
            i = 0;
          }
          if (str1 != null) {
            j = 1;
          }
          if ((i & j) != 0) {
            ((Map)localObject).put(paramString, str1);
          }
          paramString = null;
          str1 = paramString;
          continue;
        }
        if (paramString == null) {
          break label236;
        }
      }
      label233:
      str1 = paramString;
      label236:
      paramString = (String)localObject;
    }
    return localObject;
  }
  
  public static ArrayList<String> getTypeList(String paramString)
  {
    ArrayList localArrayList = new ArrayList();
    int j = paramString.indexOf("<");
    int m = 0;
    while (m < j)
    {
      a(localArrayList, paramString.substring(m, j));
      int n = j + 1;
      j = paramString.indexOf("<", n);
      int k = paramString.indexOf(",", n);
      int i = j;
      if (j == -1) {
        i = k;
      }
      j = i;
      m = n;
      if (k != -1)
      {
        j = i;
        m = n;
        if (k < i)
        {
          j = k;
          m = n;
        }
      }
    }
    a(localArrayList, paramString.substring(m, paramString.length()));
    return localArrayList;
  }
  
  public static String java2UniType(String paramString)
  {
    if ((!paramString.equals("java.lang.Integer")) && (!paramString.equals("int")))
    {
      if ((!paramString.equals("java.lang.Boolean")) && (!paramString.equals("boolean")))
      {
        if ((!paramString.equals("java.lang.Byte")) && (!paramString.equals("byte")))
        {
          if ((!paramString.equals("java.lang.Double")) && (!paramString.equals("double")))
          {
            if ((!paramString.equals("java.lang.Float")) && (!paramString.equals("float")))
            {
              if ((!paramString.equals("java.lang.Long")) && (!paramString.equals("long")))
              {
                if ((!paramString.equals("java.lang.Short")) && (!paramString.equals("short")))
                {
                  if (!paramString.equals("java.lang.Character"))
                  {
                    if (paramString.equals("java.lang.String")) {
                      return "string";
                    }
                    if (paramString.equals("java.util.List")) {
                      return "list";
                    }
                    if (paramString.equals("java.util.Map")) {
                      return "map";
                    }
                    return paramString;
                  }
                  throw new IllegalArgumentException("can not support java.lang.Character");
                }
                return "short";
              }
              return "int64";
            }
            return "float";
          }
          return "double";
        }
        return "char";
      }
      return "bool";
    }
    return "int32";
  }
  
  public static void setClassLoader(boolean paramBoolean, ClassLoader paramClassLoader)
  {
    jdField_a_of_type_Boolean = paramBoolean;
    jdField_a_of_type_JavaLangClassLoader = paramClassLoader;
  }
  
  public static String transTypeList(ArrayList<String> paramArrayList)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    int i = 0;
    while (i < paramArrayList.size())
    {
      paramArrayList.set(i, java2UniType((String)paramArrayList.get(i)));
      i += 1;
    }
    Collections.reverse(paramArrayList);
    i = 0;
    while (i < paramArrayList.size())
    {
      Object localObject = (String)paramArrayList.get(i);
      int j;
      if (((String)localObject).equals("list"))
      {
        j = i - 1;
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append("<");
        ((StringBuilder)localObject).append((String)paramArrayList.get(j));
        paramArrayList.set(j, ((StringBuilder)localObject).toString());
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append((String)paramArrayList.get(0));
        ((StringBuilder)localObject).append(">");
        paramArrayList.set(0, ((StringBuilder)localObject).toString());
      }
      else if (((String)localObject).equals("map"))
      {
        j = i - 1;
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append("<");
        ((StringBuilder)localObject).append((String)paramArrayList.get(j));
        ((StringBuilder)localObject).append(",");
        paramArrayList.set(j, ((StringBuilder)localObject).toString());
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append((String)paramArrayList.get(0));
        ((StringBuilder)localObject).append(">");
        paramArrayList.set(0, ((StringBuilder)localObject).toString());
      }
      else if (((String)localObject).equals("Array"))
      {
        j = i - 1;
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append("<");
        ((StringBuilder)localObject).append((String)paramArrayList.get(j));
        paramArrayList.set(j, ((StringBuilder)localObject).toString());
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append((String)paramArrayList.get(0));
        ((StringBuilder)localObject).append(">");
        paramArrayList.set(0, ((StringBuilder)localObject).toString());
      }
      i += 1;
    }
    Collections.reverse(paramArrayList);
    paramArrayList = paramArrayList.iterator();
    while (paramArrayList.hasNext()) {
      localStringBuffer.append((String)paramArrayList.next());
    }
    return localStringBuffer.toString();
  }
  
  public static String uni2JavaType(String paramString)
  {
    if (paramString.equals("int32")) {
      return "java.lang.Integer";
    }
    if (paramString.equals("bool")) {
      return "java.lang.Boolean";
    }
    if (paramString.equals("char")) {
      return "java.lang.Byte";
    }
    if (paramString.equals("double")) {
      return "java.lang.Double";
    }
    if (paramString.equals("float")) {
      return "java.lang.Float";
    }
    if (paramString.equals("int64")) {
      return "java.lang.Long";
    }
    if (paramString.equals("short")) {
      return "java.lang.Short";
    }
    if (paramString.equals("string")) {
      return "java.lang.String";
    }
    if (paramString.equals("list")) {
      return "java.util.List";
    }
    if (paramString.equals("map")) {
      return "java.util.Map";
    }
    return paramString;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\taf\BasicClassTypeUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */