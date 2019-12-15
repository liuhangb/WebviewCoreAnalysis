package com.tencent.turingfd.sdk.tbs;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.text.TextUtils;
import java.util.HashMap;
import java.util.Map;

public class ab
{
  public Handler a;
  
  public ab(Handler paramHandler)
  {
    this.a = paramHandler;
  }
  
  public static SharedPreferences a(Context paramContext)
  {
    return paramContext.getSharedPreferences("turingfd_conf_105578_28_tbsMini", 0);
  }
  
  public static String a(Context paramContext, String paramString)
  {
    int i = 0;
    paramString = paramContext.getSharedPreferences("turingfd_conf_105578_28_tbsMini", 0).getString(paramString, "");
    if (TextUtils.isEmpty(paramString)) {
      return paramString;
    }
    try
    {
      int j = paramString.length() / 2;
      paramContext = new byte[j];
      paramString = paramString.toCharArray();
      while (i < j)
      {
        int k = i * 2;
        int m = paramString[k];
        m = (byte)"0123456789ABCDEF".indexOf(m);
        k = paramString[(k + 1)];
        paramContext[i] = ((byte)((byte)"0123456789ABCDEF".indexOf(k) | m << 4));
        i += 1;
      }
      paramContext = l.b(paramContext, l.a());
      paramContext = new String(paramContext, "UTF-8");
      return paramContext;
    }
    catch (Throwable paramContext)
    {
      for (;;) {}
    }
    return "";
  }
  
  public x a(Context paramContext)
  {
    String str1 = a(paramContext, "101");
    if (TextUtils.isEmpty(str1)) {
      return x.a(1);
    }
    long l1 = 0L;
    try
    {
      long l2 = Long.valueOf(a(paramContext, "102")).longValue();
      l1 = l2;
    }
    catch (Throwable localThrowable)
    {
      String str2;
      String str3;
      String str4;
      x.b localb;
      for (;;) {}
    }
    str2 = a(paramContext, "103");
    str3 = a(paramContext, "104");
    str4 = a(paramContext, "105");
    paramContext = a(paramContext, "106");
    localb = x.a(0);
    localb.b = str2;
    localb.jdField_a_of_type_Long = l1;
    localb.jdField_a_of_type_JavaLangString = str1;
    localb.c = str3;
    localb.d = str4;
    localb.e = paramContext;
    return localb.a();
  }
  
  public String a(Context paramContext)
  {
    return a(paramContext, "203");
  }
  
  public void a(Context paramContext, int paramInt, long paramLong)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(paramInt);
    localStringBuilder.append("_");
    localStringBuilder.append(paramLong);
    a(paramContext, "202", localStringBuilder.toString());
  }
  
  public void a(Context paramContext, long paramLong)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("");
    localStringBuilder.append(paramLong);
    a(paramContext, "204", localStringBuilder.toString());
  }
  
  public void a(Context paramContext, x paramx)
  {
    HashMap localHashMap = new HashMap();
    localHashMap.put("101", paramx.jdField_a_of_type_JavaLangString);
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("");
    localStringBuilder.append(paramx.jdField_a_of_type_Long);
    localHashMap.put("102", localStringBuilder.toString());
    if (!TextUtils.isEmpty(paramx.b)) {
      localHashMap.put("103", paramx.b);
    }
    if (!TextUtils.isEmpty(paramx.c)) {
      localHashMap.put("104", paramx.c);
    }
    if (!TextUtils.isEmpty(paramx.d)) {
      localHashMap.put("105", paramx.d);
    }
    if (!TextUtils.isEmpty(paramx.e)) {
      localHashMap.put("106", paramx.e);
    }
    this.a.post(new aa(this, paramContext, localHashMap));
  }
  
  public final void a(Context paramContext, String paramString1, String paramString2)
  {
    HashMap localHashMap = new HashMap();
    localHashMap.put(paramString1, paramString2);
    this.a.post(new aa(this, paramContext, localHashMap));
  }
  
  public String b(Context paramContext)
  {
    return a(paramContext, "202");
  }
  
  public void b(Context paramContext, long paramLong)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("");
    localStringBuilder.append(paramLong);
    a(paramContext, "203", localStringBuilder.toString());
  }
  
  public String c(Context paramContext)
  {
    return a(paramContext, "201");
  }
  
  public void c(Context paramContext, long paramLong)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("");
    localStringBuilder.append(paramLong);
    a(paramContext, "201", localStringBuilder.toString());
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\turingfd\sdk\tbs\ab.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */