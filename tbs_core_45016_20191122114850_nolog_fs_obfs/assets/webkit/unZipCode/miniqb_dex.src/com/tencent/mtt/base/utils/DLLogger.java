package com.tencent.mtt.base.utils;

import com.tencent.basesupport.FLogger;

public class DLLogger
{
  public static void d(String paramString)
  {
    FLogger.d("QB_DOWN_LOGGER", paramString);
  }
  
  public static void d(String paramString1, String paramString2)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("QB_DOWN_LOGGER:");
    localStringBuilder.append(paramString1);
    FLogger.d(localStringBuilder.toString(), paramString2);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\base\utils\DLLogger.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */