package com.tencent.mtt;

import android.content.Context;

public class ContextHolder
{
  private static Context sAppContext;
  
  public static Context getAppContext()
  {
    return sAppContext;
  }
  
  public static void initAppContext(Context paramContext)
  {
    sAppContext = paramContext;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\ContextHolder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */