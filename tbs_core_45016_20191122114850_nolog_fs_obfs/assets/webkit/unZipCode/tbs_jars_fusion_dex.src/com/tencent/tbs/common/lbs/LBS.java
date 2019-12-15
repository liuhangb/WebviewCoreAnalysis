package com.tencent.tbs.common.lbs;

import android.content.Context;

public class LBS
{
  private static Context sAppContext;
  private static String sLibOptDir;
  private static String sLibSearchPath;
  
  public static Context getContext()
  {
    return sAppContext;
  }
  
  public static String getLibSearchPath()
  {
    return sLibSearchPath;
  }
  
  public static String getOptDir()
  {
    return sLibOptDir;
  }
  
  public static void init(Context paramContext, String paramString1, String paramString2)
  {
    if (sAppContext != null) {
      return;
    }
    sAppContext = paramContext.getApplicationContext();
    sLibSearchPath = paramString1;
    sLibOptDir = paramString2;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\lbs\LBS.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */