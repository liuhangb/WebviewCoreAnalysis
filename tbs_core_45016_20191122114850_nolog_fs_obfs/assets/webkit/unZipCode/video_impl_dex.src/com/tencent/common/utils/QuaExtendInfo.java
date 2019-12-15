package com.tencent.common.utils;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class QuaExtendInfo
{
  public static final String KEY_REF = "REF";
  public static final String REF_QB = "qb_0";
  private static String jdField_a_of_type_JavaLangString = "";
  private static ConcurrentHashMap<String, String> jdField_a_of_type_JavaUtilConcurrentConcurrentHashMap = new ConcurrentHashMap();
  private static boolean jdField_a_of_type_Boolean = false;
  
  public static String getParam(@NonNull String paramString)
  {
    return (String)jdField_a_of_type_JavaUtilConcurrentConcurrentHashMap.get(paramString);
  }
  
  public static String getParams()
  {
    if (!jdField_a_of_type_Boolean)
    {
      jdField_a_of_type_Boolean = true;
      StringBuilder localStringBuilder = new StringBuilder();
      Iterator localIterator = jdField_a_of_type_JavaUtilConcurrentConcurrentHashMap.keySet().iterator();
      while (localIterator.hasNext())
      {
        String str = (String)localIterator.next();
        localStringBuilder.append(str);
        localStringBuilder.append("=");
        localStringBuilder.append((String)jdField_a_of_type_JavaUtilConcurrentConcurrentHashMap.get(str));
        if (localIterator.hasNext()) {
          localStringBuilder.append("&");
        }
      }
      jdField_a_of_type_JavaLangString = localStringBuilder.toString();
    }
    return jdField_a_of_type_JavaLangString;
  }
  
  public static String getREF()
  {
    return getParams();
  }
  
  public static void setCurrentREF(String paramString)
  {
    setParam("REF", paramString);
  }
  
  public static void setParam(@NonNull String paramString1, @Nullable String paramString2)
  {
    if (TextUtils.isEmpty(paramString2)) {
      jdField_a_of_type_JavaUtilConcurrentConcurrentHashMap.remove(paramString1);
    } else {
      jdField_a_of_type_JavaUtilConcurrentConcurrentHashMap.put(paramString1, paramString2);
    }
    jdField_a_of_type_Boolean = false;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\utils\QuaExtendInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */