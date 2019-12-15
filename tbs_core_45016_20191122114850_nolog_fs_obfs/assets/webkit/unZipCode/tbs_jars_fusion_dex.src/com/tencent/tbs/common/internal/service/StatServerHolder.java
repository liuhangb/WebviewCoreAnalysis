package com.tencent.tbs.common.internal.service;

import android.text.TextUtils;
import java.util.HashMap;

public class StatServerHolder
{
  public static IQBSmttService sStatReporter;
  
  public static void reportErrorLog(String paramString, Throwable paramThrowable)
  {
    if (paramThrowable == null) {
      return;
    }
    String str1 = new String();
    localObject1 = "";
    str2 = str1;
    try
    {
      String str3 = paramThrowable.getMessage();
      int i = 1;
      for (;;)
      {
        localObject2 = str1;
        localObject3 = str3;
        str2 = str1;
        localObject1 = str3;
        if (i >= paramThrowable.getStackTrace().length) {
          break;
        }
        str2 = str1;
        localObject1 = str3;
        localObject2 = new StringBuilder();
        str2 = str1;
        localObject1 = str3;
        ((StringBuilder)localObject2).append(str1);
        str2 = str1;
        localObject1 = str3;
        ((StringBuilder)localObject2).append("\\");
        str2 = str1;
        localObject1 = str3;
        ((StringBuilder)localObject2).append(paramThrowable.getStackTrace()[i]);
        str2 = str1;
        localObject1 = str3;
        str1 = ((StringBuilder)localObject2).toString();
        i += 1;
      }
    }
    catch (Throwable paramThrowable)
    {
      for (;;)
      {
        Object localObject2 = str2;
        Object localObject3 = localObject1;
      }
    }
    if (TextUtils.isEmpty((CharSequence)localObject2)) {
      return;
    }
    paramThrowable = new HashMap();
    paramThrowable.put("error_stack", localObject2);
    paramThrowable.put("error_msg", localObject3);
    statWithBeacon(paramString, paramThrowable);
  }
  
  public static void statWithBeacon(String paramString, HashMap<String, String> paramHashMap)
  {
    IQBSmttService localIQBSmttService = sStatReporter;
    if (localIQBSmttService != null) {
      localIQBSmttService.upLoadToBeacon(paramString, paramHashMap);
    }
  }
  
  public static void userBehaviorStatistics(String paramString)
  {
    IQBSmttService localIQBSmttService = sStatReporter;
    if (localIQBSmttService != null) {
      localIQBSmttService.userBehaviorStatistics(paramString);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\internal\service\StatServerHolder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */